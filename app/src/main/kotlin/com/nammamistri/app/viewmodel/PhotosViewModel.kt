package com.nammamistri.app.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * ViewModel for Photos Fragment
 * Stores image URIs per site in SharedPreferences as JSON
 */
class PhotosViewModel(application: Application) : AndroidViewModel(application) {
    
    private val sharedPrefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    // Photos for current site
    private val _sitePhotos = MutableLiveData<List<SitePhoto>>()
    val sitePhotos: LiveData<List<SitePhoto>> = _sitePhotos
    
    // Current site ID
    private var currentSiteId: Long = 0
    
    /**
     * Load photos for a specific site from SharedPreferences
     */
    fun loadPhotos(siteId: Long) {
        currentSiteId = siteId
        val photos = getPhotosFromPrefs(siteId)
        _sitePhotos.value = photos.sortedByDescending { it.timestamp }
    }
    
    /**
     * Add a photo for the current site
     */
    fun addPhoto(siteId: Long, uri: String, filePath: String, description: String = "") {
        val photo = SitePhoto(
            id = System.currentTimeMillis(),
            uri = uri,
            filePath = filePath,
            description = description,
            timestamp = System.currentTimeMillis()
        )
        
        val currentPhotos = getPhotosFromPrefs(siteId).toMutableList()
        currentPhotos.add(0, photo) // Add at the beginning
        
        savePhotosToPrefs(siteId, currentPhotos)
        
        if (siteId == currentSiteId) {
            _sitePhotos.value = currentPhotos.sortedByDescending { it.timestamp }
        }
    }
    
    /**
     * Delete a photo
     */
    fun deletePhoto(siteId: Long, photo: SitePhoto) {
        val currentPhotos = getPhotosFromPrefs(siteId).toMutableList()
        currentPhotos.removeAll { it.id == photo.id }
        
        savePhotosToPrefs(siteId, currentPhotos)
        
        // Delete the actual file
        try {
            val file = File(photo.filePath)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        if (siteId == currentSiteId) {
            _sitePhotos.value = currentPhotos.sortedByDescending { it.timestamp }
        }
    }
    
    /**
     * Update photo description
     */
    fun updatePhotoDescription(siteId: Long, photoId: Long, description: String) {
        val currentPhotos = getPhotosFromPrefs(siteId).toMutableList()
        val index = currentPhotos.indexOfFirst { it.id == photoId }
        
        if (index >= 0) {
            currentPhotos[index] = currentPhotos[index].copy(description = description)
            savePhotosToPrefs(siteId, currentPhotos)
            
            if (siteId == currentSiteId) {
                _sitePhotos.value = currentPhotos.sortedByDescending { it.timestamp }
            }
        }
    }
    
    /**
     * Get photos for a site from SharedPreferences
     */
    private fun getPhotosFromPrefs(siteId: Long): List<SitePhoto> {
        val key = "${KEY_PREFIX}$siteId"
        val json = sharedPrefs.getString(key, null) ?: return emptyList()
        
        return try {
            val jsonArray = JSONArray(json)
            val photos = mutableListOf<SitePhoto>()
            
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                photos.add(
                    SitePhoto(
                        id = obj.getLong("id"),
                        uri = obj.getString("uri"),
                        filePath = obj.getString("filePath"),
                        description = obj.optString("description", ""),
                        timestamp = obj.getLong("timestamp")
                    )
                )
            }
            
            photos
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Save photos for a site to SharedPreferences
     */
    private fun savePhotosToPrefs(siteId: Long, photos: List<SitePhoto>) {
        val key = "${KEY_PREFIX}$siteId"
        val jsonArray = JSONArray()
        
        photos.forEach { photo ->
            val obj = JSONObject().apply {
                put("id", photo.id)
                put("uri", photo.uri)
                put("filePath", photo.filePath)
                put("description", photo.description)
                put("timestamp", photo.timestamp)
            }
            jsonArray.put(obj)
        }
        
        sharedPrefs.edit()
            .putString(key, jsonArray.toString())
            .apply()
    }
    
    /**
     * Get photo count for a site
     */
    fun getPhotoCount(siteId: Long): Int {
        return getPhotosFromPrefs(siteId).size
    }
    
    /**
     * Get all photo URIs for sharing
     */
    fun getAllPhotoUris(siteId: Long): List<Uri> {
        return getPhotosFromPrefs(siteId).mapNotNull { photo ->
            try {
                Uri.parse(photo.uri)
            } catch (e: Exception) {
                null
            }
        }
    }
    
    /**
     * Clear all photos for a site
     */
    fun clearSitePhotos(siteId: Long) {
        // Delete all photo files
        getPhotosFromPrefs(siteId).forEach { photo ->
            try {
                val file = File(photo.filePath)
                if (file.exists()) {
                    file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        // Clear from SharedPreferences
        val key = "${KEY_PREFIX}$siteId"
        sharedPrefs.edit()
            .remove(key)
            .apply()
        
        if (siteId == currentSiteId) {
            _sitePhotos.value = emptyList()
        }
    }
    
    companion object {
        private const val PREFS_NAME = "nammamistri_photos"
        private const val KEY_PREFIX = "photos_"
    }
}

/**
 * Photo data class for storage
 */
data class SitePhoto(
    val id: Long,
    val uri: String,
    val filePath: String,
    val description: String,
    val timestamp: Long
) {
    fun getUri(): Uri = Uri.parse(uri)
}
