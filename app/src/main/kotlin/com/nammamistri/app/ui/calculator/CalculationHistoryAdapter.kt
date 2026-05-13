package com.nammamistri.app.ui.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamistri.app.data.model.Calculation
import com.nammamistri.app.databinding.ItemCalculationHistoryBinding
import com.nammamistri.app.utils.format
import com.nammamistri.app.utils.toFormattedDate

/**
 * RecyclerView Adapter for calculation history
 */
class CalculationHistoryAdapter(
    private val onItemClick: (Calculation) -> Unit,
    private val onDeleteClick: (Calculation) -> Unit
) : ListAdapter<Calculation, CalculationHistoryAdapter.ViewHolder>(DiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalculationHistoryBinding.inflate(
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
        private val binding: ItemCalculationHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(calculation: Calculation) {
            with(binding) {
                textProjectName.text = calculation.projectName
                textCalculationType.text = calculation.calculationType.name.replace("_", " ")
                textArea.text = "${calculation.area.format(2)} sqft"
                textQuantity.text = "${calculation.quantity.format(2)} ${calculation.unit}"
                textDate.text = calculation.createdAt.toFormattedDate()
                
                // Set icon based on type
                val iconRes = when (calculation.calculationType) {
                    com.nammamistri.app.data.model.CalculationType.BRICK_WORK -> "🧱"
                    com.nammamistri.app.data.model.CalculationType.PLASTERING -> "🏗️"
                    com.nammamistri.app.data.model.CalculationType.CONCRETE -> "🪨"
                    com.nammamistri.app.data.model.CalculationType.PAINTING -> "🎨"
                    com.nammamistri.app.data.model.CalculationType.TILE_WORK -> "🔲"
                    else -> "📐"
                }
                textIcon.text = iconRes
                
                root.setOnClickListener { onItemClick(calculation) }
                buttonDelete.setOnClickListener { onDeleteClick(calculation) }
            }
        }
    }
    
    private class DiffCallback : DiffUtil.ItemCallback<Calculation>() {
        override fun areItemsTheSame(oldItem: Calculation, newItem: Calculation): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Calculation, newItem: Calculation): Boolean {
            return oldItem == newItem
        }
    }
}
