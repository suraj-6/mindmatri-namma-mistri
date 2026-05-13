package com.nammamistri.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility functions for image handling
 */
object ImageUtils {
    
    /**
     * Create a temporary image file for camera capture
     */
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat(
            Constants.DATE_FORMAT_FILE, 
            Locale.getDefault()
        ).format(Date())
        
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        
        return File.createTempFile(
            "IMG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
    
    /**
     * Get URI for file using FileProvider
     */
    fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
    
    /**
     * Create project photos directory
     */
    fun getProjectPhotosDir(context: Context, projectName: String): File {
        val photosDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "${Constants.PHOTOS_DIRECTORY}/$projectName"
        )
        if (!photosDir.exists()) {
            photosDir.mkdirs()
        }
        return photosDir
    }
    
    /**
     * Compress and save image
     */
    fun compressImage(
        context: Context,
        uri: Uri,
        maxWidth: Int = 1920,
        maxHeight: Int = 1080,
        quality: Int = 85
    ): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            // Calculate new dimensions maintaining aspect ratio
            val ratio = minOf(
                maxWidth.toFloat() / originalBitmap.width,
                maxHeight.toFloat() / originalBitmap.height
            )
            
            val newWidth = (originalBitmap.width * ratio).toInt()
            val newHeight = (originalBitmap.height * ratio).toInt()
            
            val scaledBitmap = Bitmap.createScaledBitmap(
                originalBitmap, 
                newWidth, 
                newHeight, 
                true
            )
            
            // Save compressed image
            val outputFile = createImageFile(context)
            val outputStream = FileOutputStream(outputFile)
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.close()
            
            // Recycle bitmaps
            if (originalBitmap != scaledBitmap) {
                originalBitmap.recycle()
            }
            scaledBitmap.recycle()
            
            outputFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Create thumbnail from image
     */
    fun createThumbnail(
        context: Context,
        uri: Uri,
        size: Int = 200
    ): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            // Calculate crop dimensions for square thumbnail
            val dimension = minOf(originalBitmap.width, originalBitmap.height)
            val x = (originalBitmap.width - dimension) / 2
            val y = (originalBitmap.height - dimension) / 2
            
            val croppedBitmap = Bitmap.createBitmap(
                originalBitmap, 
                x, y, 
                dimension, dimension
            )
            
            val thumbnailBitmap = Bitmap.createScaledBitmap(
                croppedBitmap,
                size,
                size,
                true
            )
            
            // Save thumbnail
            val timeStamp = SimpleDateFormat(
                Constants.DATE_FORMAT_FILE,
                Locale.getDefault()
            ).format(Date())
            
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val thumbnailFile = File(storageDir, "thumb_${timeStamp}.jpg")
            
            val outputStream = FileOutputStream(thumbnailFile)
            thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            outputStream.close()
            
            // Recycle bitmaps
            originalBitmap.recycle()
            if (croppedBitmap != thumbnailBitmap) {
                croppedBitmap.recycle()
            }
            thumbnailBitmap.recycle()
            
            thumbnailFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Delete image file
     */
    fun deleteImage(path: String): Boolean {
        return try {
            File(path).delete()
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Get file size in readable format
     */
    fun getFileSize(file: File): String {
        val sizeInBytes = file.length()
        return when {
            sizeInBytes < 1024 -> "$sizeInBytes B"
            sizeInBytes < 1024 * 1024 -> "${sizeInBytes / 1024} KB"
            else -> "${sizeInBytes / (1024 * 1024)} MB"
        }
    }
}
