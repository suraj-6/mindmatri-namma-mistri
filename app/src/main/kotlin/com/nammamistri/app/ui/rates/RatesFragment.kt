package com.nammamistri.app.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.R
import com.nammamistri.app.data.model.MaterialRate
import com.nammamistri.app.databinding.FragmentRatesBinding
import com.nammamistri.app.databinding.DialogAddRateBinding
import com.nammamistri.app.viewmodel.RatesViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Rates Fragment for managing material and labor rates
 * Features:
 * - Editable rate list
 * - Add custom materials
 * - Last updated timestamp
 */
class RatesFragment : Fragment() {
    
    private var _binding: FragmentRatesBinding? = null
    private val binding get() = _binding!!
    
    private val ratesViewModel: RatesViewModel by viewModels()
    
    private lateinit var ratesAdapter: RatesAdapter
    
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }
    
    private fun setupRecyclerView() {
        ratesAdapter = RatesAdapter(
            onUpdateRate = { rate, newPrice ->
                updateRate(rate, newPrice)
            },
            onDeleteRate = { rate ->
                confirmDeleteRate(rate)
            }
        )
        
        binding.recyclerViewRates.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ratesAdapter
        }
    }
    
    private fun setupClickListeners() {
        // FAB to add custom rate
        binding.fabAddRate.setOnClickListener {
            showAddRateDialog()
        }
    }
    
    private fun showAddRateDialog() {
        val dialogBinding = DialogAddRateBinding.inflate(layoutInflater)
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("+ ಹೊಸ ದರ ಸೇರಿಸಿ / Add Custom Rate")
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val name = dialogBinding.inputMaterialName.text.toString().trim()
                val unit = dialogBinding.inputUnit.text.toString().trim()
                val priceStr = dialogBinding.inputPrice.text.toString().trim()
                
                if (name.isBlank() || unit.isBlank() || priceStr.isBlank()) {
                    Toast.makeText(requireContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                
                val price = priceStr.toDoubleOrNull()
                if (price == null || price <= 0) {
                    Toast.makeText(requireContext(), "ಸರಿಯಾದ ಬೆಲೆ ನಮೂದಿಸಿ / Enter valid price", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                
                val rate = MaterialRate(
                    materialName = name,
                    unit = unit,
                    pricePerUnit = price,
                    lastUpdated = System.currentTimeMillis()
                )
                
                ratesViewModel.insertRate(rate)
                Toast.makeText(requireContext(), getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
    
    private fun updateRate(rate: MaterialRate, newPrice: Double) {
        if (newPrice <= 0) {
            Toast.makeText(requireContext(), "ಸರಿಯಾದ ಬೆಲೆ ನಮೂದಿಸಿ / Enter valid price", Toast.LENGTH_SHORT).show()
            return
        }
        
        val updatedRate = rate.copy(
            pricePerUnit = newPrice,
            lastUpdated = System.currentTimeMillis()
        )
        
        ratesViewModel.updateRate(updatedRate)
        Toast.makeText(requireContext(), "ನವೀಕರಿಸಲಾಗಿದೆ / Updated: ${rate.materialName}", Toast.LENGTH_SHORT).show()
    }
    
    private fun confirmDeleteRate(rate: MaterialRate) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete))
            .setMessage("'${rate.materialName}' ಅಳಿಸಬೇಕೇ? / Delete '${rate.materialName}'?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                ratesViewModel.deleteRate(rate)
                Toast.makeText(requireContext(), getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun observeViewModel() {
        // Observe all rates
        ratesViewModel.allMaterialRates.observe(viewLifecycleOwner) { rates ->
            ratesAdapter.submitList(rates)
            binding.textNoRates.visibility = if (rates.isEmpty()) View.VISIBLE else View.GONE
            
            // Update last modified date
            if (rates.isNotEmpty()) {
                val latestUpdate = rates.maxOf { it.lastUpdated }
                binding.textLastUpdated.text = "Last Updated: ${dateFormatter.format(Date(latestUpdate))}"
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
