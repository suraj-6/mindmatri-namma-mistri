package com.nammamistri.app.ui.site

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.databinding.DialogAddSiteBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * Dialog for adding a new construction site
 */
class AddSiteDialog : DialogFragment() {
    
    private var _binding: DialogAddSiteBinding? = null
    private val binding get() = _binding!!
    
    private var onSiteAddedListener: ((Site) -> Unit)? = null
    private var selectedDate: Long = System.currentTimeMillis()
    
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddSiteBinding.inflate(LayoutInflater.from(context))
        
        // Set initial date
        binding.textSelectedDate.text = dateFormatter.format(Date(selectedDate))
        
        // Date picker click
        binding.textSelectedDate.setOnClickListener {
            showDatePicker()
        }
        
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("ನೂತನ ಸೈಟ್ / New Site")
            .setView(binding.root)
            .setPositiveButton(getString(R.string.save), null) // We'll handle this manually
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
            .apply {
                setOnShowListener {
                    // Override positive button to prevent auto-dismiss
                    getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                        if (validateAndSave()) {
                            dismiss()
                        }
                    }
                }
            }
    }
    
    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("ಆರಂಭ ದಿನಾಂಕ / Start Date")
            .setSelection(selectedDate)
            .build()
        
        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = selection
            binding.textSelectedDate.text = dateFormatter.format(Date(selection))
        }
        
        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }
    
    private fun validateAndSave(): Boolean {
        val name = binding.inputSiteName.text.toString().trim()
        val location = binding.inputLocation.text.toString().trim()
        
        if (name.isBlank()) {
            binding.inputSiteName.error = "ಸೈಟ್ ಹೆಸರು ಅಗತ್ಯ / Site name required"
            return false
        }
        
        if (location.isBlank()) {
            binding.inputLocation.error = "ಸ್ಥಳ ಅಗತ್ಯ / Location required"
            return false
        }
        
        val site = Site(
            name = name,
            location = location,
            startDate = selectedDate,
            isActive = true
        )
        
        onSiteAddedListener?.invoke(site)
        return true
    }
    
    fun setOnSiteAddedListener(listener: (Site) -> Unit) {
        onSiteAddedListener = listener
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
