package com.nammamistri.app.ui.rates

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamistri.app.data.model.MaterialRate
import com.nammamistri.app.databinding.ItemRateBinding
import java.text.NumberFormat
import java.util.*

/**
 * Adapter for displaying material rates with inline editing
 */
class RatesAdapter(
    private val onUpdateRate: (MaterialRate, Double) -> Unit,
    private val onDeleteRate: (MaterialRate) -> Unit
) : ListAdapter<MaterialRate, RatesAdapter.ViewHolder>(DiffCallback()) {
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ViewHolder(
        private val binding: ItemRateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(rate: MaterialRate) {
            with(binding) {
                // Material info
                textMaterialName.text = rate.materialName
                textUnit.text = rate.unit
                
                // Set price in EditText (without currency symbol for editing)
                inputPrice.setText(rate.pricePerUnit.toInt().toString())
                
                // Display formatted price as hint/label
                textCurrentPrice.text = "ಪ್ರಸ್ತುತ: ${currencyFormatter.format(rate.pricePerUnit)}"
                
                // Update button
                buttonUpdate.setOnClickListener {
                    val newPriceStr = inputPrice.text.toString()
                    val newPrice = newPriceStr.toDoubleOrNull()
                    
                    if (newPrice != null && newPrice > 0) {
                        onUpdateRate(rate, newPrice)
                    }
                }
                
                // Delete button (long press on card)
                root.setOnLongClickListener {
                    onDeleteRate(rate)
                    true
                }
            }
        }
    }
    
    private class DiffCallback : DiffUtil.ItemCallback<MaterialRate>() {
        override fun areItemsTheSame(oldItem: MaterialRate, newItem: MaterialRate): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: MaterialRate, newItem: MaterialRate): Boolean {
            return oldItem == newItem
        }
    }
}
