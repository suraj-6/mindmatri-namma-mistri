package com.nammamistri.app.ui.site

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.databinding.ItemSiteCardBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for displaying construction sites with stats
 */
class SiteAdapter(
    private val onOpenSite: (Site) -> Unit,
    private val onMarkComplete: (Site) -> Unit,
    private val onDeleteSite: (Site) -> Unit
) : ListAdapter<Site, SiteAdapter.ViewHolder>(DiffCallback()) {
    
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val siteColors = intArrayOf(
        0xFF6366F1.toInt(), // Indigo
        0xFFF59E0B.toInt(), // Amber
        0xFF10B981.toInt(), // Emerald
        0xFFEF4444.toInt(), // Red
        0xFF8B5CF6.toInt(), // Violet
        0xFF06B6D4.toInt()  // Cyan
    )
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSiteCardBinding.inflate(
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
        private val binding: ItemSiteCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(site: Site) {
            with(binding) {
                // Site info
                textSiteName.text = site.name
                textLocation.text = site.location
                textStartDate.text = "Started: ${dateFormatter.format(Date(site.startDate))}"
                
                // Status badge
                if (site.isActive) {
                    chipStatus.text = "ACTIVE"
                    chipStatus.setChipBackgroundColorResource(R.color.success)
                } else {
                    chipStatus.text = "COMPLETED"
                    chipStatus.setChipBackgroundColorResource(R.color.info)
                }
                
                // Assign random color dot
                val color = siteColors[adapterPosition % siteColors.size]
                viewColorDot.setBackgroundColor(color)
                
                // Stats (will be updated later with real data)
                textWorkers.text = "Workers: 0"
                textLogs.text = "Logs: 0"
                textPhotos.text = "Photos: 0"
                
                // Buttons
                buttonOpenSite.setOnClickListener {
                    onOpenSite(site)
                }
                
                buttonMarkComplete.setOnClickListener {
                    onMarkComplete(site)
                }
                
                buttonDelete.setOnClickListener {
                    onDeleteSite(site)
                }
            }
        }
    }
    
    private class DiffCallback : DiffUtil.ItemCallback<Site>() {
        override fun areItemsTheSame(oldItem: Site, newItem: Site): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Site, newItem: Site): Boolean {
            return oldItem == newItem
        }
    }
}
