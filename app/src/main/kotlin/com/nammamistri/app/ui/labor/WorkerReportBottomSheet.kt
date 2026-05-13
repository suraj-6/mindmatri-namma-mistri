package com.nammamistri.app.ui.labor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Worker
import com.nammamistri.app.data.model.WageEntry
import com.nammamistri.app.databinding.BottomSheetWorkerReportBinding
import com.nammamistri.app.viewmodel.LaborViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Bottom sheet for worker report and sharing
 */
class WorkerReportBottomSheet : BottomSheetDialogFragment() {
    
    private var _binding: BottomSheetWorkerReportBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var worker: Worker
    private lateinit var wageEntries: List<WageEntry>
    private lateinit var laborViewModel: LaborViewModel
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get arguments
        worker = arguments?.getParcelable(ARG_WORKER) ?: return
        wageEntries = arguments?.getParcelableArrayList(ARG_ENTRIES) ?: emptyList()
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetWorkerReportBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        // Worker info
        binding.textWorkerName.text = worker.name
        binding.textDailyWage.text = "${currencyFormatter.format(worker.dailyWage)}/day"
        
        // Calculate stats
        val daysPresent = wageEntries.count { it.isPresent }
        val totalEarned = daysPresent * worker.dailyWage
        val totalAdvance = wageEntries.sumOf { it.advancePayment }
        val balanceDue = totalEarned - totalAdvance
        
        // Display stats
        binding.textDaysPresent.text = daysPresent.toString()
        binding.textTotalEarned.text = currencyFormatter.format(totalEarned)
        binding.textTotalAdvance.text = currencyFormatter.format(totalAdvance)
        binding.textBalanceDue.text = currencyFormatter.format(balanceDue)
        
        // Color code balance
        val balanceColor = if (balanceDue >= 0) {
            resources.getColor(R.color.success, null)
        } else {
            resources.getColor(R.color.error, null)
        }
        binding.textBalanceDue.setTextColor(balanceColor)
        
        // Generate print-ready summary
        val summaryText = """
            ${worker.name} | Days: $daysPresent | Earned: ${currencyFormatter.format(totalEarned)} | Advance: ${currencyFormatter.format(totalAdvance)} | Balance: ${currencyFormatter.format(balanceDue)}
        """.trimIndent()
        
        binding.textPrintSummary.text = summaryText
    }
    
    private fun setupClickListeners() {
        binding.buttonShare.setOnClickListener {
            shareReport()
        }
        
        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }
    
    private fun shareReport() {
        val daysPresent = wageEntries.count { it.isPresent }
        val totalEarned = daysPresent * worker.dailyWage
        val totalAdvance = wageEntries.sumOf { it.advancePayment }
        val balanceDue = totalEarned - totalAdvance
        
        val message = """
            NammaMistri Worker Report
            ========================
            Name: ${worker.name}
            Phone: ${worker.phoneNumber}
            Daily Wage: ${currencyFormatter.format(worker.dailyWage)}
            
            Summary:
            Days Present: $daysPresent
            Total Earned: ${currencyFormatter.format(totalEarned)}
            Total Advance: ${currencyFormatter.format(totalAdvance)}
            Balance Due: ${currencyFormatter.format(balanceDue)}
            
            Generated on ${SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(Date())}
        """.trimIndent()
        
        // Try WhatsApp first, fallback to SMS/share sheet
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
            putExtra("jid", "whatsapp") // WhatsApp specific
        }
        
        try {
            startActivity(Intent.createChooser(intent, "ಹಂಚಿಕೊಳ್ಳಿ / Share Report"))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "ಹಂಚಲು ಸಾಧ್ಯವಾಗಲಿಲ್ಲ / Could not share", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        private const val ARG_WORKER = "worker"
        private const val ARG_ENTRIES = "entries"
        
        fun newInstance(worker: Worker, entries: List<WageEntry>): WorkerReportBottomSheet {
            return WorkerReportBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_WORKER, worker)
                    putParcelableArrayList(ARG_ENTRIES, ArrayList(entries))
                }
            }
        }
    }
}
