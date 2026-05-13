package com.nammamistri.app.ui.labor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamistri.app.data.model.Labor
import com.nammamistri.app.data.model.LaborSkill
import com.nammamistri.app.databinding.ItemLaborBinding
import com.nammamistri.app.utils.formatCurrency
import com.nammamistri.app.utils.formatPhone

/**
 * RecyclerView Adapter for laborers list
 */
class LaborAdapter(
    private val onItemClick: (Labor) -> Unit,
    private val onAttendanceClick: (Labor) -> Unit,
    private val onPaymentClick: (Labor) -> Unit
) : ListAdapter<Labor, LaborAdapter.ViewHolder>(DiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLaborBinding.inflate(
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
        private val binding: ItemLaborBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(labor: Labor) {
            with(binding) {
                // Set initials avatar
                textAvatar.text = labor.name.take(2).uppercase()
                
                // Set avatar background color based on skill
                val bgColor = when (labor.skill) {
                    LaborSkill.MASON -> 0xFF6366F1.toInt()
                    LaborSkill.HELPER -> 0xFF10B981.toInt()
                    LaborSkill.CARPENTER -> 0xFFF59E0B.toInt()
                    LaborSkill.PLUMBER -> 0xFF3B82F6.toInt()
                    LaborSkill.ELECTRICIAN -> 0xFFEF4444.toInt()
                    LaborSkill.PAINTER -> 0xFF8B5CF6.toInt()
                    else -> 0xFF6B7280.toInt()
                }
                cardAvatar.setCardBackgroundColor(bgColor)
                
                textName.text = labor.name
                textSkill.text = labor.skill.displayName
                textPhone.text = labor.phone.formatPhone()
                textWage.text = labor.dailyWage.formatCurrency() + "/day"
                
                // Set skill icon
                val skillIcon = when (labor.skill) {
                    LaborSkill.MASON -> "🧱"
                    LaborSkill.HELPER -> "👷"
                    LaborSkill.CARPENTER -> "🪚"
                    LaborSkill.PLUMBER -> "🔧"
                    LaborSkill.ELECTRICIAN -> "⚡"
                    LaborSkill.PAINTER -> "🎨"
                    LaborSkill.TILE_WORKER -> "🔲"
                    LaborSkill.WELDER -> "🔥"
                    LaborSkill.SUPERVISOR -> "📋"
                    else -> "👤"
                }
                textSkillIcon.text = skillIcon
                
                // Click listeners
                root.setOnClickListener { onItemClick(labor) }
                buttonAttendance.setOnClickListener { onAttendanceClick(labor) }
                buttonPayment.setOnClickListener { onPaymentClick(labor) }
            }
        }
    }
    
    private class DiffCallback : DiffUtil.ItemCallback<Labor>() {
        override fun areItemsTheSame(oldItem: Labor, newItem: Labor): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Labor, newItem: Labor): Boolean {
            return oldItem == newItem
        }
    }
}
