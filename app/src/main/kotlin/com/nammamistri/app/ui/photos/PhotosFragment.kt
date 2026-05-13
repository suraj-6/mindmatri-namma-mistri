package com.nammamistri.app.ui.photos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.databinding.FragmentPhotosBinding
import com.nammamistri.app.ui.site.AddSiteDialog
import com.nammamistri.app.viewmodel.PhotosViewModel
import com.nammamistri.app.viewmodel.SharedSiteViewModel
import com.nammamistri.app.viewmodel.SitePhoto
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Photos Fragment for capturing and managing site photos
 * Enhanced with multi-site management (Add/Delete/Select) directly in the tab
 */
class PhotosFragment : Fragment() {
    
    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    
    private val sharedSiteViewModel: SharedSiteViewModel by activityViewModels()
    private val photosViewModel: PhotosViewModel by viewModels()
    
    private lateinit var photoAdapter: PhotoGridAdapter
    private var isSpinnerInitializing = true
    
    // Current photo file being captured
    private var currentPhotoFile: File? = null
    private var currentPhotoUri: Uri? = null
    
    // Camera permission launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            launchCamera()
        } else {
            Toast.makeText(requireContext(), "ಕ್ಯಾಮೆರಾ ಅನುಮತಿ ಅಗತ್ಯ / Camera permission required", Toast.LENGTH_SHORT).show()
        }
    }
    
    // Camera capture launcher
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoUri != null && currentPhotoFile != null) {
            // Photo captured successfully
            val siteId = sharedSiteViewModel.selectedSiteId.value ?: return@registerForActivityResult
            
            photosViewModel.addPhoto(
                siteId = siteId,
                uri = currentPhotoUri!!.toString(),
                filePath = currentPhotoFile!!.absolutePath
            )
            
            Toast.makeText(requireContext(), getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupClickListeners()
        setupSiteSpinner()
        observeViewModels()
    }
    
    private fun setupRecyclerView() {
        photoAdapter = PhotoGridAdapter(
            onPhotoClick = { photo ->
                showPhotoDetail(photo)
            },
            onDeleteClick = { photo ->
                confirmDeletePhoto(photo)
            }
        )
        
        binding.recyclerViewPhotos.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = photoAdapter
        }
    }
    
    private fun setupClickListeners() {
        // Button to take photo
        binding.buttonTakePhotoFull.setOnClickListener {
            checkCameraPermissionAndCapture()
        }
        
        // Share button
        binding.buttonShare.setOnClickListener {
            shareAllPhotos()
        }

        // Add site button
        binding.buttonAddSite.setOnClickListener {
            showAddSiteDialog()
        }

        // Delete site button
        binding.buttonDeleteSite.setOnClickListener {
            confirmDeleteCurrentSite()
        }
    }

    private fun setupSiteSpinner() {
        binding.spinnerSite.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isSpinnerInitializing) {
                    isSpinnerInitializing = false
                    return
                }
                val sites = sharedSiteViewModel.allActiveSites.value
                if (!sites.isNullOrEmpty() && position < sites.size) {
                    sharedSiteViewModel.selectSite(sites[position])
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun showAddSiteDialog() {
        val dialog = AddSiteDialog()
        dialog.setOnSiteAddedListener { site ->
            sharedSiteViewModel.createAndSelectSite(site.name, site.location) {
                Toast.makeText(requireContext(), "ಸೈಟ್ ಸೇರಿಸಲಾಗಿದೆ / Site Added", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show(parentFragmentManager, "AddSiteDialog")
    }

    private fun confirmDeleteCurrentSite() {
        val currentSite = sharedSiteViewModel.selectedSite.value ?: return
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("'${currentSite.name}' ಸೈಟ್ ಅಳಿಸಬೇಕೇ? / Delete site '${currentSite.name}'?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                sharedSiteViewModel.deleteSite(currentSite)
                Toast.makeText(requireContext(), "ಸೈಟ್ ಅಳಿಸಲಾಗಿದೆ / Site deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun checkCameraPermissionAndCapture() {
        val siteId = sharedSiteViewModel.selectedSiteId.value
        if (siteId == null || siteId <= 0) {
            Toast.makeText(requireContext(), getString(R.string.please_select_site), Toast.LENGTH_SHORT).show()
            return
        }
        
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchCamera()
            }
            else -> {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
            }
        }
    }
    
    private fun launchCamera() {
        val siteId = sharedSiteViewModel.selectedSiteId.value ?: return
        
        try {
            // Create image file
            currentPhotoFile = createImageFile(siteId)
            currentPhotoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                currentPhotoFile!!
            )
            
            takePictureLauncher.launch(currentPhotoUri)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "ಕ್ಯಾಮೆರಾ ತೆರೆಯಲಾಗಲಿಲ್ಲ / Could not open camera", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun createImageFile(siteId: Long): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "NammaMistri_${siteId}_${timeStamp}.jpg"
        
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        
        // Create NammaMistri subdirectory
        val nammaMistriDir = File(storageDir, "NammaMistri")
        if (!nammaMistriDir.exists()) {
            nammaMistriDir.mkdirs()
        }
        
        return File(nammaMistriDir, fileName)
    }
    
    private fun showPhotoDetail(photo: SitePhoto) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val dateStr = dateFormat.format(Date(photo.timestamp))
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("📷 ಫೋಟೋ ವಿವರ / Photo Detail")
            .setMessage("📅 ದಿನಾಂಕ / Date: $dateStr")
            .setPositiveButton(getString(R.string.ok), null)
            .setNeutralButton(getString(R.string.share)) { _, _ ->
                sharePhoto(photo)
            }
            .setNegativeButton(getString(R.string.delete)) { _, _ ->
                confirmDeletePhoto(photo)
            }
            .show()
    }
    
    private fun confirmDeletePhoto(photo: SitePhoto) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("ಈ ಫೋಟೋ ಅಳಿಸಬೇಕೇ? / Delete this photo?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                val siteId = sharedSiteViewModel.selectedSiteId.value ?: return@setPositiveButton
                photosViewModel.deletePhoto(siteId, photo)
                Toast.makeText(requireContext(), getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun sharePhoto(photo: SitePhoto) {
        try {
            val file = File(photo.filePath)
            if (!file.exists()) {
                Toast.makeText(requireContext(), "ಫೈಲ್ ಕಂಡುಬಂದಿಲ್ಲ / File not found", Toast.LENGTH_SHORT).show()
                return
            }
            
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            )
            
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, "NammaMistri - Site Photo")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            startActivity(Intent.createChooser(shareIntent, "ಫೋಟೋ ಹಂಚಿಕೊಳ್ಳಿ / Share Photo"))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "ಹಂಚಲು ಸಾಧ್ಯವಾಗಲಿಲ್ಲ / Could not share", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun shareAllPhotos() {
        val photos = photosViewModel.sitePhotos.value
        if (photos.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "ಯಾವುದೇ ಫೋಟೋಗಳಿಲ್ಲ / No photos to share", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val uris = ArrayList<Uri>()
            
            photos.forEach { photo ->
                val file = File(photo.filePath)
                if (file.exists()) {
                    val uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        file
                    )
                    uris.add(uri)
                }
            }
            
            if (uris.isEmpty()) {
                Toast.makeText(requireContext(), "ಯಾವುದೇ ಫೋಟೋಗಳಿಲ್ಲ / No photos to share", Toast.LENGTH_SHORT).show()
                return
            }
            
            val siteName = sharedSiteViewModel.selectedSite.value?.name ?: "Site"
            
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE
                type = "image/*"
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
                putExtra(Intent.EXTRA_TEXT, "NammaMistri - $siteName\n${photos.size} ಫೋಟೋಗಳು / photos")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            startActivity(Intent.createChooser(shareIntent, "ಮಾಲೀಕರೊಂದಿಗೆ ಹಂಚಿಕೊಳ್ಳಿ / Share with Owner"))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "ಹಂಚಲು ಸಾಧ್ಯವಾಗಲಿಲ್ಲ / Could not share", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun observeViewModels() {
        // Observe all sites to populate spinner
        sharedSiteViewModel.allActiveSites.observe(viewLifecycleOwner) { sites ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                sites.map { it.name }
            )
            binding.spinnerSite.adapter = adapter
            
            // Set selection based on currently selected site
            val selectedSite = sharedSiteViewModel.selectedSite.value
            if (selectedSite != null) {
                val index = sites.indexOfFirst { it.id == selectedSite.id }
                if (index >= 0) {
                    isSpinnerInitializing = true // Prevent re-triggering selectSite
                    binding.spinnerSite.setSelection(index)
                }
            }
        }

        // Observe selected site to load photos
        sharedSiteViewModel.selectedSite.observe(viewLifecycleOwner) { site ->
            if (site != null) {
                photosViewModel.loadPhotos(site.id)
                
                // Also update spinner if it's not in sync
                val sites = sharedSiteViewModel.allActiveSites.value
                if (!sites.isNullOrEmpty()) {
                    val index = sites.indexOfFirst { it.id == site.id }
                    if (index >= 0 && binding.spinnerSite.selectedItemPosition != index) {
                        isSpinnerInitializing = true
                        binding.spinnerSite.setSelection(index)
                    }
                }
            }
        }
        
        // Observe photos
        photosViewModel.sitePhotos.observe(viewLifecycleOwner) { photos ->
            photoAdapter.submitList(photos)
            binding.layoutEmptyPhotos.visibility = if (photos.isEmpty()) View.VISIBLE else View.GONE
            binding.textPhotoCount.text = "${photos.size} ಫೋಟೋಗಳು / photos"
            
            // Show/hide share button based on photo count
            binding.buttonShare.visibility = if (photos.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
