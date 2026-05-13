package com.nammamistri.app.ui.labor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Worker
import com.nammamistri.app.databinding.FragmentLaborBinding
import com.nammamistri.app.viewmodel.LaborViewModel
import com.nammamistri.app.viewmodel.SharedSiteViewModel
import com.nammamistri.app.viewmodel.WorkerWithBalance
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Labor/Team Fragment for managing workers and attendance
 * Features:
 * - Worker list with attendance toggles
 * - Advance payment tracking
 * - Balance calculation (earned - advance)
 * - Site daily totals
 */
class LaborFragment : Fragment() {
    
    private var _binding: FragmentLaborBinding? = null
    private val binding get() = _binding!!
    
    private val sharedSiteViewModel: SharedSiteViewModel by activityViewModels()
    private val laborViewModel: LaborViewModel by viewModels()
    
    private lateinit var workerAdapter: WorkerAdapter
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    private val dateFormatter = SimpleDateFormat("dd MMMM yyyy, EEEE", Locale("en", "IN"))
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaborBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupHeader()
        setupRecyclerView()
        setupClickListeners()
        observeViewModels()
    }
    
    private fun setupHeader() {
        // Set current date
        binding.textCurrentDate.text = dateFormatter.format(Date())
    }
    
    private fun setupRecyclerView() {
        workerAdapter = WorkerAdapter(
            onAttendanceChanged = { worker, isPresent ->
                markAttendance(worker, isPresent)
            },
            onAddAdvance = { worker, amount ->
                addAdvance(worker, amount)
            },
            onWorkerClick = { worker ->
                showWorkerDetails(worker)
            },
            onDeleteWorker = { worker ->
                confirmDeleteWorker(worker)
            }
        )
        
        binding.recyclerViewWorkers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workerAdapter
        }
    }
    
    private fun setupClickListeners() {
        // FAB to add new worker
        binding.fabAddWorker.setOnClickListener {
            showAddWorkerDialog()
        }

        // Empty state button to add first worker
        binding.buttonAddFirstWorker.setOnClickListener {
            showAddWorkerDialog()
        }
    }
    
    private fun showAddWorkerDialog() {
        val currentSiteId = sharedSiteViewModel.selectedSiteId.value
        if (currentSiteId == null || currentSiteId <= 0) {
            Toast.makeText(requireContext(), getString(R.string.please_select_site), Toast.LENGTH_SHORT).show()
            return
        }
        
        val dialog = AddWorkerDialog.newInstance(currentSiteId)
        dialog.setOnWorkerAddedListener { worker ->
            laborViewModel.addWorker(worker)
            Toast.makeText(requireContext(), getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
        }
        dialog.show(parentFragmentManager, "AddWorkerDialog")
    }
    
    private fun markAttendance(worker: Worker, isPresent: Boolean) {
        val today = getTodayStartMillis()
        laborViewModel.markAttendance(worker.id, today, isPresent)
    }
    
    private fun addAdvance(worker: Worker, amount: Double) {
        if (amount <= 0) {
            Toast.makeText(requireContext(), "ಮೊತ್ತ ನಮೂದಿಸಿ / Enter amount", Toast.LENGTH_SHORT).show()
            return
        }
        
        val today = getTodayStartMillis()
        laborViewModel.addAdvance(worker.id, today, amount)
        Toast.makeText(requireContext(), "ಅಡ್ವಾನ್ಸ್ ಸೇರಿಸಲಾಗಿದೆ / Advance added: ${currencyFormatter.format(amount)}", Toast.LENGTH_SHORT).show()
    }
    
    private fun showWorkerDetails(worker: Worker) {
        laborViewModel.getWorkerBalance(worker) { balance ->
            requireActivity().runOnUiThread {
                val message = """
                    📱 ಫೋನ್ / Phone: ${worker.phoneNumber}
                    💰 ದಿನದ ಕೂಲಿ / Daily Wage: ${currencyFormatter.format(worker.dailyWage)}
                    
                    ━━━ ಸಾರಾಂಶ / Summary ━━━
                    📅 ಹಾಜರಿ ದಿನಗಳು / Days Present: ${balance.daysPresent}
                    💵 ಒಟ್ಟು ಸಂಪಾದನೆ / Total Earned: ${currencyFormatter.format(balance.totalEarned)}
                    💸 ಒಟ್ಟು ಅಡ್ವಾನ್ಸ್ / Total Advance: ${currencyFormatter.format(balance.totalAdvance)}
                    
                    ${getBalanceText(balance.balanceDue)}
                """.trimIndent()
                
                com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                    .setTitle("👷 ${worker.name}")
                    .setMessage(message)
                    .setPositiveButton(getString(R.string.ok), null)
                    .setNeutralButton(getString(R.string.edit)) { _, _ ->
                        // TODO: Edit worker dialog
                    }
                    .setNegativeButton(getString(R.string.delete)) { _, _ ->
                        confirmDeleteWorker(worker)
                    }
                    .show()
            }
        }
    }
    
    private fun getBalanceText(balance: Double): String {
        return if (balance >= 0) {
            "✅ ಬಾಕಿ (ಪಾವತಿಸಬೇಕು) / Balance Due: ${currencyFormatter.format(balance)}"
        } else {
            "⚠️ ಹೆಚ್ಚುವರಿ ಅಡ್ವಾನ್ಸ್ / Excess Advance: ${currencyFormatter.format(-balance)}"
        }
    }
    
    private fun confirmDeleteWorker(worker: Worker) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("${worker.name} ಅನ್ನು ಅಳಿಸಬೇಕೇ? / Delete ${worker.name}?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                laborViewModel.deleteWorker(worker)
                Toast.makeText(requireContext(), getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun getTodayStartMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    private fun observeViewModels() {
        // Observe selected site
        sharedSiteViewModel.selectedSite.observe(viewLifecycleOwner) { site ->
            site?.let {
                binding.textSiteName.text = "📍 ${it.name}"
                laborViewModel.loadWorkersBySite(it.id)
            }
        }
        
        // Observe workers with balances
        laborViewModel.workersWithBalances.observe(viewLifecycleOwner) { workers ->
            workerAdapter.submitList(workers)
            binding.layoutEmptyState.visibility = if (workers.isEmpty()) View.VISIBLE else View.GONE
            
            // Calculate site totals
            updateSiteTotals(workers)
        }
    }
    
    private fun updateSiteTotals(workers: List<WorkerWithBalance>) {
        var totalWagesToday = 0.0
        var totalBalanceDue = 0.0
        var presentCount = 0
        
        workers.forEach { workerWithBalance ->
            if (workerWithBalance.isPresentToday == true) {
                totalWagesToday += workerWithBalance.worker.dailyWage
                presentCount++
            }
            totalBalanceDue += workerWithBalance.balanceDue
        }
        
        binding.textTodayPresent.text = "$presentCount ಹಾಜರು / Present"
        binding.textTodayWages.text = currencyFormatter.format(totalWagesToday)
        binding.textTotalBalance.text = currencyFormatter.format(totalBalanceDue)
        
        // Color code balance
        val balanceColor = if (totalBalanceDue >= 0) {
            resources.getColor(R.color.success, null)
        } else {
            resources.getColor(R.color.error, null)
        }
        binding.textTotalBalance.setTextColor(balanceColor)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
