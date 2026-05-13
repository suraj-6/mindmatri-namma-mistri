package com.nammamistri.app.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nammamistri.app.data.model.SitePhoto
import com.nammamistri.app.databinding.ItemPhotoBinding
import com.nammamistri.app.utils.toFormattedDate

/**
 * RecyclerView Adapter for photos grid
 */
class PhotosAdapter(
    private val onItemClick: (SitePhoto) -> Unit,
    private val onDeleteClick: (SitePhoto) -> Unit
) : ListAdapter<SitePhoto, PhotosAdapter.ViewHolder>(DiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(
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
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(photo: SitePhoto) {
            with(binding) {
                // Load image with Glide
                Glide.with(imagePhoto)
                    .load(photo.photoUri)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imagePhoto)
                
                textProjectName.text = photo.projectName
                textCategory.text = photo.category.displayName
                textDate.text = photo.takenAt.toFormattedDate("dd MMM")
                
                // Category badge color
                val badgeColor = when (photo.category) {
                    com.nammamistri.app.data.model.PhotoCategory.BEFORE -> 0xFFEF4444.toInt()
                    com.nammamistri.app.data.model.PhotoCategory.AFTER -> 0xFF10B981.toInt()
                    com.nammamistri.app.data.model.PhotoCategory.PROGRESS -> 0xFFF59E0B.toInt()
                    com.nammamistri.app.data.model.PhotoCategory.ISSUE -> 0xFFDC2626.toInt()
                    else -> 0xFF6366F1.toInt()
                }
                chipCategory.setChipBackgroundColor(
                    android.content.res.ColorStateList.valueOf(badgeColor)
                )
                chipCategory.text = photo.category.displayName
                
                root.setOnClickListener { onItemClick(photo) }
                root.setOnLongClickListener {
                    onDeleteClick(photo)
                    true
                }
            }
        }
    }
    
    private class DiffCallback : DiffUtil.ItemCallback<SitePhoto>() {
        override fun areItemsTheSame(oldItem: SitePhoto, newItem: SitePhoto): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: SitePhoto, newItem: SitePhoto): Boolean {
            return oldItem == newItem
        }
    }
}
