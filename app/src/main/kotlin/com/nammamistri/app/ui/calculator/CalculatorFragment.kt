package com.nammamistri.app.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.MainActivity
import com.nammamistri.app.R
import com.nammamistri.app.data.model.MaterialLog
import com.nammamistri.app.databinding.FragmentCalculatorBinding
import com.nammamistri.app.utils.ConstructionFormulas
import com.nammamistri.app.viewmodel.CalculatorViewModel
import com.nammamistri.app.viewmodel.CostBreakdown
import com.nammamistri.app.viewmodel.RatesViewModel
import com.nammamistri.app.viewmodel.SharedSiteViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class CalculatorFragment : Fragment() {
    
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    
    private val sharedSiteViewModel: SharedSiteViewModel by activityViewModels()
    private val calculatorViewModel: CalculatorViewModel by viewModels()
    private val ratesViewModel: RatesViewModel by viewModels()
    
    private lateinit var historyAdapter: MaterialLogAdapter
    
    private var currentBricks: Int = 0
    private var currentCement: Int = 0
    private var currentSandLoads: Double = 0.0
    private var currentCostBreakdown: CostBreakdown? = null
    
    private val wallThicknessOptions = listOf(
        WallThicknessOption("4.5 ಇಂಚು (ಅರ್ಧ ಇಟ್ಟಿಗೆ) / 4.5 inch (Half Brick)", 4.5),
        WallThicknessOption("9 ಇಂಚು (ಪೂರ್ಣ ಇಟ್ಟಿಗೆ) / 9 inch (Full Brick)", 9.0),
        WallThicknessOption("13.5 ಇಂಚು (ಒಂದೂವರೆ ಇಟ್ಟಿಗೆ) / 13.5 inch (One and Half)", 13.5)
    )
    
    private var selectedThickness: Double = 9.0
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSiteSpinner()
        setupThicknessSpinner()
        setupHistoryRecyclerView()
        setupClickListeners()
        observeViewModels()
        binding.cardResult.visibility = View.GONE
    }
    
    private fun setupSiteSpinner() {
        sharedSiteViewModel.allActiveSites.observe(viewLifecycleOwner) { sites ->
            if (sites.isNotEmpty()) {
                val siteNames = sites.map { it.name }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, siteNames)
                binding.spinnerSite.adapter = adapter
                sharedSiteViewModel.selectedSite.value?.let { currentSite ->
                    val index = sites.indexOfFirst { it.id == currentSite.id }
                    if (index >= 0) binding.spinnerSite.setSelection(index)
                }
            }
        }
        binding.spinnerSite.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sites = sharedSiteViewModel.allActiveSites.value ?: return
                if (position in sites.indices) {
                    sharedSiteViewModel.selectSite(sites[position])
                    calculatorViewModel.setCurrentSite(sites[position])
                }
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }
    
    private fun setupThicknessSpinner() {
        val thicknessNames = wallThicknessOptions.map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, thicknessNames)
        binding.spinnerThickness.adapter = adapter
        binding.spinnerThickness.setSelection(1)
        binding.spinnerThickness.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedThickness = wallThicknessOptions[position].thicknessInch
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }
    
    private fun setupHistoryRecyclerView() {
        historyAdapter = MaterialLogAdapter(
            onItemClick = { log -> showLogDetails(log) },
            onDeleteClick = { log -> confirmDeleteLog(log) }
        )
        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
            isNestedScrollingEnabled = false
        }
    }
    
    private fun setupClickListeners() {
        binding.buttonCalculate.setOnClickListener { performCalculation() }
        binding.buttonSave.setOnClickListener { saveCalculation() }
        binding.buttonClear.setOnClickListener { clearInputs() }
    }
    
    private fun performCalculation() {
        val lengthStr = binding.inputLength.text.toString()
        val heightStr = binding.inputHeight.text.toString()
        if (lengthStr.isBlank() || heightStr.isBlank()) {
            Toast.makeText(requireContext(), "ದೂರ ನಮೂದಿಸಿ / Enter dimensions", Toast.LENGTH_SHORT).show()
            return
        }
        val length = lengthStr.toDoubleOrNull()
        val height = heightStr.toDoubleOrNull()
        if (length == null || height == null || length <= 0 || height <= 0) {
            Toast.makeText(requireContext(), "ಸಕಾರಾತ್ಮಕ ಸಂಖ್ಯೆಗಳನ್ನು ಮಾತ್ರ ನಮೂದಿಸಿ / Enter positive numbers only", Toast.LENGTH_SHORT).show()
            return
        }
        currentBricks = ConstructionFormulas.calculateBricks(length, height, selectedThickness)
        currentCement = ConstructionFormulas.calculateCement(length, height, selectedThickness)
        currentSandLoads = ConstructionFormulas.calculateSand(currentCement)
        ratesViewModel.calculateCost(currentBricks, currentCement, currentSandLoads) { costBreakdown ->
            currentCostBreakdown = costBreakdown
            requireActivity().runOnUiThread { displayResults() }
        }
    }
    
    private fun displayResults() {
        val formatter = DecimalFormat("#,##,###")
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        binding.textBricksValue.text = "${formatter.format(currentBricks)} ${getString(R.string.nos)}"
        binding.textCementValue.text = "${currentCement} ${getString(R.string.bags)}"
        binding.textSandValue.text = String.format("%.2f ${getString(R.string.loads)}", currentSandLoads)
        currentCostBreakdown?.let { cost ->
            binding.textCostBreakdown.text = """
                🧱 ಇಟ್ಟಿಗೆ / Bricks: ${currencyFormatter.format(cost.brickCost)}
                   (${currentBricks} × ₹${cost.brickRate.toInt()}/1000)
                🏗️ ಸಿಮೆಂಟ್ / Cement: ${currencyFormatter.format(cost.cementCost)}
                   (${currentCement} bags × ₹${cost.cementRate.toInt()})
                🏖️ ಮರಳು / Sand: ${currencyFormatter.format(cost.sandCost)}
                   (${String.format("%.2f", currentSandLoads)} loads × ₹${cost.sandRate.toInt()})
            """.trimIndent()
            binding.textTotalCost.text = currencyFormatter.format(cost.totalCost)
        }
        binding.cardResult.visibility = View.VISIBLE
        binding.cardResult.alpha = 0f
        binding.cardResult.animate().alpha(1f).setDuration(300).start()
        binding.scrollView.post { binding.scrollView.smoothScrollTo(0, binding.cardResult.top) }
    }
    
    private fun saveCalculation() {
        val currentSite = sharedSiteViewModel.selectedSite.value
        if (currentSite == null) {
            Toast.makeText(requireContext(), getString(R.string.please_select_site), Toast.LENGTH_SHORT).show()
            (requireActivity() as? MainActivity)?.sharedSiteViewModel?.showSiteSelection()
            return
        }
        if (currentBricks == 0) {
            Toast.makeText(requireContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }
        val length = binding.inputLength.text.toString().toDoubleOrNull() ?: 0.0
        val height = binding.inputHeight.text.toString().toDoubleOrNull() ?: 0.0
        val width = binding.inputWidth.text.toString().toDoubleOrNull() ?: 0.0
        val thicknessDisplay = wallThicknessOptions.find { it.thicknessInch == selectedThickness }?.displayName ?: "${selectedThickness} inch"
        val materialLog = MaterialLog(
            siteId = currentSite.id,
            bricks = currentBricks,
            cementBags = currentCement,
            sandLoads = currentSandLoads,
            wallLength = length,
            wallWidth = width,
            wallHeight = height,
            wallThickness = thicknessDisplay
        )
        calculatorViewModel.saveMaterialLog(materialLog)
        Toast.makeText(requireContext(), getString(R.string.calculation_saved), Toast.LENGTH_SHORT).show()
    }
    
    private fun clearInputs() {
        binding.inputLength.text?.clear()
        binding.inputWidth.text?.clear()
        binding.inputHeight.text?.clear()
        binding.cardResult.visibility = View.GONE
        currentBricks = 0
        currentCement = 0
        currentSandLoads = 0.0
        currentCostBreakdown = null
    }
    
    private fun showLogDetails(log: MaterialLog) {
        val formatter = DecimalFormat("#,##,###")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.wall_details))
            .setMessage("""
                📐 ಗೋಡೆ ಅಳತೆ / Wall Dimensions:
                • ಉದ್ದ / Length: ${log.wallLength} m
                • ಎತ್ತರ / Height: ${log.wallHeight} m
                • ದಪ್ಪ / Thickness: ${log.wallThickness}
                
                📦 ಸಾಮಗ್ರಿಗಳು / Materials:
                • ಇಟ್ಟಿಗೆ / Bricks: ${formatter.format(log.bricks)}
                • ಸಿಮೆಂಟ್ / Cement: ${log.cementBags} bags
                • ಮರಳು / Sand: ${String.format("%.2f", log.sandLoads)} loads
                
                📅 ದಿನಾಂಕ / Date: ${formatDate(log.calculatedOn)}
            """.trimIndent())
            .setPositiveButton(getString(R.string.ok), null)
            .setNegativeButton(getString(R.string.delete)) { _, _ -> confirmDeleteLog(log) }
            .show()
    }
    
    private fun confirmDeleteLog(log: MaterialLog) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("ಈ ಲೆಕ್ಕಾಚಾರವನ್ನು ಅಳಿಸಬೇಕೇ? / Delete this calculation?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                calculatorViewModel.deleteMaterialLog(log)
                Toast.makeText(requireContext(), getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun formatDate(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }
    
    private fun observeViewModels() {
        sharedSiteViewModel.selectedSite.observe(viewLifecycleOwner) { site ->
            site?.let {
                calculatorViewModel.setCurrentSite(it)
                binding.textCurrentSite.text = "📍 ${it.name}"
            }
        }
        calculatorViewModel.materialLogs.observe(viewLifecycleOwner) { logs: List<MaterialLog> ->
            historyAdapter.submitList(logs)
            binding.textNoHistory.visibility = if (logs.isEmpty()) View.VISIBLE else View.GONE
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class WallThicknessOption(val displayName: String, val thicknessInch: Double)
