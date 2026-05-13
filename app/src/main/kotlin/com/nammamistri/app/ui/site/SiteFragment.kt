package com.nammamistri.app.ui.site

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.databinding.FragmentSiteBinding
import com.nammamistri.app.viewmodel.SiteViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Site Management Fragment for managing construction sites
 * Features:
 * - List all sites with stats
 * - Add new sites
 * - Mark sites complete
 * - Delete sites
 */
class SiteFragment : Fragment() {
    
    private var _binding: FragmentSiteBinding? = null
    private val binding get() = _binding!!
    
    private val siteViewModel: SiteViewModel by activityViewModels()
    
    private lateinit var siteAdapter: SiteAdapter
    
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiteBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }
    
    private fun setupRecyclerView() {
        siteAdapter = SiteAdapter(
            onOpenSite = { site ->
                siteViewModel.selectSite(site)
                // Close this fragment (go back to main tabs)
                parentFragmentManager.popBackStack()
            },
            onMarkComplete = { site ->
                confirmMarkComplete(site)
            },
            onDeleteSite = { site ->
                confirmDeleteSite(site)
            }
        )
        
        binding.recyclerViewSites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = siteAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.fabAddSite.setOnClickListener {
            showAddSiteDialog()
        }
    }
    
    private fun showAddSiteDialog() {
        val dialog = AddSiteDialog()
        dialog.setOnSiteAddedListener { site ->
            siteViewModel.createSite(site)
        }
        dialog.show(parentFragmentManager, "AddSiteDialog")
    }
    
    private fun confirmMarkComplete(site: Site) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("MARK COMPLETE")
            .setMessage("ಈ ಸೈಟ್ ಅನ್ನು ಪೂರ್ಣಗೊಳಿಸಿದಂತೆ ಗುರುತಿಸಬೇಕೇ?\nMark '${site.name}' as complete?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                siteViewModel.markComplete(site.id)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun confirmDeleteSite(site: Site) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("ಈ ಸೈಟ್ ಅನ್ನು ಅಳಿಸಬೇಕೇ? ಎಲ್ಲಾ ಡೇಟಾ ಕಳೆಯುತ್ತದೆ!\nDelete '${site.name}'? All data will be lost!")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                siteViewModel.deleteSite(site.id)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun observeViewModel() {
        siteViewModel.allSites.observe(viewLifecycleOwner) { sites ->
            siteAdapter.submitList(sites)
            binding.textNoSites.visibility = if (sites.isEmpty()) View.VISIBLE else View.GONE
        }
        
        siteViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
