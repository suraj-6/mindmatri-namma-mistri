package com.nammamistri.app.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nammamistri.app.databinding.ItemPhotoGridBinding
import com.nammamistri.app.viewmodel.SitePhoto
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for displaying photos in a grid
 */
class PhotoGridAdapter(
    private val onPhotoClick: (SitePhoto) -> Unit,
    private val onDeleteClick: (SitePhoto) -> Unit
) : ListAdapter<SitePhoto, PhotoGridAdapter.ViewHolder>(DiffCallback()) {
    
    private val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoGridBinding.inflate(
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
        private val binding: ItemPhotoGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(photo: SitePhoto) {
            with(binding) {
                // Load image with Glide
                val file = File(photo.filePath)
                
                Glide.with(imagePhoto)
                    .load(file)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imagePhoto)
                
                // Date overlay
                textDate.text = dateFormatter.format(Date(photo.timestamp))
                
                // Click listeners
                root.setOnClickListener { onPhotoClick(photo) }
                buttonDelete.setOnClickListener { onDeleteClick(photo) }
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
