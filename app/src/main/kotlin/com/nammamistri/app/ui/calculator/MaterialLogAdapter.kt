package com.nammamistri.app.ui.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamistri.app.data.model.MaterialLog
import com.nammamistri.app.databinding.ItemMaterialLogBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * RecyclerView Adapter for Material Log history
 */
class MaterialLogAdapter(
    private val onItemClick: (MaterialLog) -> Unit,
    private val onDeleteClick: (MaterialLog) -> Unit
) : ListAdapter<MaterialLog, MaterialLogAdapter.ViewHolder>(DiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMaterialLogBinding.inflate(
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
        private val binding: ItemMaterialLogBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private val numberFormatter = DecimalFormat("#,##,###")
        private val dateFormatter = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        
        fun bind(log: MaterialLog) {
            with(binding) {
                // Wall info
                textWallDimensions.text = "${log.wallLength}m × ${log.wallHeight}m"
                textWallThickness.text = log.wallThickness
                
                // Materials
                textBricks.text = "🧱 ${numberFormatter.format(log.bricks)}"
                textCement.text = "🏗️ ${log.cementBags} bags"
                textSand.text = "🏖️ ${String.format("%.1f", log.sandLoads)} loads"
                
                // Date
                textDate.text = dateFormatter.format(Date(log.calculatedOn))
                
                // Click listeners
                root.setOnClickListener { onItemClick(log) }
                buttonDelete.setOnClickListener { onDeleteClick(log) }
            }
        }
    }
    
    private class DiffCallback : DiffUtil.ItemCallback<MaterialLog>() {
        override fun areItemsTheSame(oldItem: MaterialLog, newItem: MaterialLog): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: MaterialLog, newItem: MaterialLog): Boolean {
            return oldItem == newItem
        }
    }
}
