package com.nammamistri.app.ui.labor

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Worker
import com.nammamistri.app.databinding.DialogAddWorkerBinding

/**
 * Dialog for adding a new worker
 */
class AddWorkerDialog : DialogFragment() {
    
    private var _binding: DialogAddWorkerBinding? = null
    private val binding get() = _binding!!
    
    private var siteId: Long = 0
    private var onWorkerAddedListener: ((Worker) -> Unit)? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        siteId = arguments?.getLong(ARG_SITE_ID) ?: 0
    }
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddWorkerBinding.inflate(LayoutInflater.from(context))
        
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("👷 ${getString(R.string.add_worker)}")
            .setView(binding.root)
            .setPositiveButton(getString(R.string.save), null) // Set to null to handle manually
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
            .apply {
                setOnShowListener {
                    // Override positive button to prevent auto-dismiss on validation failure
                    getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                        if (validateAndSave()) {
                            dismiss()
                        }
                    }
                }
            }
    }
    
    private fun validateAndSave(): Boolean {
        val name = binding.inputWorkerName.text.toString().trim()
        val wageStr = binding.inputDailyWage.text.toString().trim()
        val phone = binding.inputPhoneNumber.text.toString().trim()
        
        // Validation
        if (name.isBlank()) {
            binding.inputWorkerName.error = "ಹೆಸರು ಅಗತ್ಯ / Name required"
            return false
        }
        
        if (wageStr.isBlank()) {
            binding.inputDailyWage.error = "ಕೂಲಿ ಅಗತ್ಯ / Wage required"
            return false
        }
        
        val wage = wageStr.toDoubleOrNull()
        if (wage == null || wage <= 0) {
            binding.inputDailyWage.error = "ಸರಿಯಾದ ಮೊತ್ತ ನಮೂದಿಸಿ / Enter valid amount"
            return false
        }
        
        // Create worker
        val worker = Worker(
            siteId = siteId,
            name = name,
            dailyWage = wage,
            phoneNumber = phone,
            joiningDate = System.currentTimeMillis()
        )
        
        onWorkerAddedListener?.invoke(worker)
        return true
    }
    
    fun setOnWorkerAddedListener(listener: (Worker) -> Unit) {
        onWorkerAddedListener = listener
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        private const val ARG_SITE_ID = "site_id"
        
        fun newInstance(siteId: Long): AddWorkerDialog {
            return AddWorkerDialog().apply {
                arguments = Bundle().apply {
                    putLong(ARG_SITE_ID, siteId)
                }
            }
        }
    }
}
