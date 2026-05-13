package com.nammamistri.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.nammamistri.app.data.db.AppDatabase
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.data.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Site Management
 * Handles site creation, selection, completion, and deletion
 */
class SiteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: AppRepository = AppRepository(AppDatabase.getDatabase(application))
    
    // All sites (active and completed)
    val allSites: LiveData<List<Site>> = repository.allSites
    
    // Selected site ID (shared across fragments)
    private val _selectedSiteId = MutableLiveData<Long>()
    val selectedSiteId: LiveData<Long> = _selectedSiteId
    
    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    // Selected site
    val selectedSite: LiveData<Site?> = _selectedSiteId.switchMap { siteId ->
        if (siteId > 0) {
            repository.getSiteByIdLiveData(siteId)
        } else {
            MutableLiveData(null)
        }
    }
    
    init {
        // Initialize with first site if available
        allSites.observeForever { sites ->
            if (sites.isNotEmpty() && _selectedSiteId.value == null) {
                _selectedSiteId.value = sites[0].id
            }
        }
    }
    
    /**
     * Select a site
     */
    fun selectSite(site: Site) {
        _selectedSiteId.value = site.id
    }
    
    /**
     * Select site by ID
     */
    fun selectSiteById(siteId: Long) {
        _selectedSiteId.value = siteId
    }
    
    /**
     * Create a new site
     */
    fun createSite(site: Site) {
        viewModelScope.launch {
            _isLoading.value = true
            val siteId = repository.insertSite(site)
            _selectedSiteId.value = siteId
            _isLoading.value = false
        }
    }
    
    /**
     * Mark a site as complete (inactive)
     */
    fun markComplete(siteId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val site = repository.getSiteById(siteId)
            site?.let {
                val updatedSite = it.copy(isActive = false)
                repository.updateSite(updatedSite)
            }
            _isLoading.value = false
        }
    }
    
    /**
     * Delete a site and all related data (cascade delete)
     */
    fun deleteSite(siteId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val site = repository.getSiteById(siteId)
            site?.let {
                repository.deleteSite(it)
                if (_selectedSiteId.value == siteId) {
                    allSites.value?.firstOrNull()?.let { newSite ->
                        _selectedSiteId.value = newSite.id
                    } ?: run {
                        _selectedSiteId.value = -1L
                    }
                }
            }
            _isLoading.value = false
        }
    }
    
    /**
     * Update an existing site
     */
    fun updateSite(site: Site) {
        viewModelScope.launch {
            repository.updateSite(site)
        }
    }
    
    /**
     * Get site statistics (workers, logs, photos count)
     */
    fun getSiteStats(siteId: Long, callback: (SiteStats) -> Unit) {
        viewModelScope.launch {
            val workerCount = repository.getWorkerCountBySite(siteId)
            val logCount = 0
            val photoCount = 0
            
            callback(
                SiteStats(
                    workerCount = workerCount,
                    logCount = logCount,
                    photoCount = photoCount
                )
            )
        }
    }
}

/**
 * Site statistics data class
 */
data class SiteStats(
    val workerCount: Int,
    val logCount: Int,
    val photoCount: Int
)
