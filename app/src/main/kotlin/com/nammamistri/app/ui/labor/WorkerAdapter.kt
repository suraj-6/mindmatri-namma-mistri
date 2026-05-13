package com.nammamistri.app.ui.labor

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Worker
import com.nammamistri.app.databinding.ItemWorkerCardBinding
import com.nammamistri.app.viewmodel.WorkerWithBalance
import java.text.NumberFormat
import java.util.*

/**
 * Adapter for displaying worker cards with attendance and balance
 */
class WorkerAdapter(
    private val onAttendanceChanged: (Worker, Boolean) -> Unit,
    private val onAddAdvance: (Worker, Double) -> Unit,
    private val onWorkerClick: (Worker) -> Unit,
    private val onDeleteWorker: (Worker) -> Unit
) : ListAdapter<WorkerWithBalance, WorkerAdapter.ViewHolder>(DiffCallback()) {
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkerCardBinding.inflate(
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
        private val binding: ItemWorkerCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: WorkerWithBalance) {
            val worker = item.worker
            val context = binding.root.context
            
            with(binding) {
                // Worker info
                textWorkerName.text = worker.name
                textDailyWage.text = "${currencyFormatter.format(worker.dailyWage)}/day"
                
                // Avatar initials
                textAvatar.text = worker.name.take(2).uppercase()
                
                // Attendance buttons state
                updateAttendanceButtons(item.isPresentToday)
                
                // Balance summary
                textDaysPresent.text = "${item.daysPresent}"
                textTotalEarned.text = currencyFormatter.format(item.totalEarned)
                textTotalAdvance.text = currencyFormatter.format(item.totalAdvance)
                textBalanceDue.text = currencyFormatter.format(kotlin.math.abs(item.balanceDue))
                
                // Balance color and prefix
                if (item.balanceDue >= 0) {
                    textBalanceDue.setTextColor(ContextCompat.getColor(context, R.color.success))
                    textBalanceLabel.text = "ಬಾಕಿ / Due"
                } else {
                    textBalanceDue.setTextColor(ContextCompat.getColor(context, R.color.error))
                    textBalanceLabel.text = "ಹೆಚ್ಚು / Excess"
                }
                
                // Click listeners
                buttonPresent.setOnClickListener {
                    onAttendanceChanged(worker, true)
                }
                
                buttonAbsent.setOnClickListener {
                    onAttendanceChanged(worker, false)
                }
                
                buttonAddAdvance.setOnClickListener {
                    val amountStr = inputAdvance.text.toString()
                    val amount = amountStr.toDoubleOrNull() ?: 0.0
                    if (amount > 0) {
                        onAddAdvance(worker, amount)
                        inputAdvance.text?.clear()
                    }
                }
                
                root.setOnClickListener {
                    onWorkerClick(worker)
                }
                
                buttonDelete.setOnClickListener {
                    onDeleteWorker(worker)
                }
                
                // Long press for report
                root.setOnLongClickListener {
                    // In a real implementation, we would pass the wage entries
                    // For now, we'll just show a toast indicating the feature
                    android.widget.Toast.makeText(
                        context, 
                        "Long press detected - Report feature would open here", 
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    true
                }
            }
        }
        
        private fun updateAttendanceButtons(isPresent: Boolean?) {
            val context = binding.root.context
            
            when (isPresent) {
                true -> {
                    // Present selected
                    binding.buttonPresent.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.success)
                    )
                    binding.buttonPresent.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    binding.buttonAbsent.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.divider)
                    )
                    binding.buttonAbsent.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                }
                false -> {
                    // Absent selected
                    binding.buttonAbsent.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.error)
                    )
                    binding.buttonAbsent.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    binding.buttonPresent.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.divider)
                    )
                    binding.buttonPresent.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                }
                null -> {
                    // No selection yet
                    binding.buttonPresent.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.divider)
                    )
                    binding.buttonPresent.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                    binding.buttonAbsent.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.divider)
                    )
                    binding.buttonAbsent.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                }
            }
        }
    }
    
    private class DiffCallback : DiffUtil.ItemCallback<WorkerWithBalance>() {
        override fun areItemsTheSame(oldItem: WorkerWithBalance, newItem: WorkerWithBalance): Boolean {
            return oldItem.worker.id == newItem.worker.id
        }
        
        override fun areContentsTheSame(oldItem: WorkerWithBalance, newItem: WorkerWithBalance): Boolean {
            return oldItem == newItem
        }
    }
}
