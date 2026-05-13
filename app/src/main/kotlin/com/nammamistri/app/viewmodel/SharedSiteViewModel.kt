package com.nammamistri.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nammamistri.app.data.db.AppDatabase
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.data.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * Shared ViewModel scoped to MainActivity
 * Holds the currently selected site across all fragments
 */
class SharedSiteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: AppRepository
    
    // All active sites for selection
    val allActiveSites: LiveData<List<Site>>
    
    // Currently selected site
    private val _selectedSite = MutableLiveData<Site?>()
    val selectedSite: LiveData<Site?> = _selectedSite
    
    // Selected site ID for easier access
    private val _selectedSiteId = MutableLiveData<Long>()
    val selectedSiteId: LiveData<Long> = _selectedSiteId
    
    // Site selection dialog visibility
    private val _showSiteSelectionDialog = MutableLiveData<Boolean>()
    val showSiteSelectionDialog: LiveData<Boolean> = _showSiteSelectionDialog
    
    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(database)
        allActiveSites = repository.allActiveSites
    }
    
    /**
     * Select a site
     */
    fun selectSite(site: Site) {
        _selectedSite.value = site
        _selectedSiteId.value = site.id
        _showSiteSelectionDialog.value = false
    }
    
    /**
     * Select site by ID
     */
    fun selectSiteById(siteId: Long) {
        viewModelScope.launch {
            val site = repository.getSiteById(siteId)
            site?.let {
                _selectedSite.postValue(it)
                _selectedSiteId.postValue(it.id)
            }
        }
    }
    
    /**
     * Create a new site and select it
     */
    fun createAndSelectSite(name: String, location: String, onComplete: (Site) -> Unit) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val site = Site(
                name = name,
                location = location,
                startDate = System.currentTimeMillis(),
                isActive = true
            )
            val siteId = repository.insertSite(site)
            val newSite = repository.getSiteById(siteId)
            newSite?.let {
                _selectedSite.postValue(it)
                _selectedSiteId.postValue(it.id)
                onComplete(it)
            }
            _isLoading.postValue(false)
            _showSiteSelectionDialog.postValue(false)
        }
    }
    
    /**
     * Show site selection dialog
     */
    fun showSiteSelection() {
        _showSiteSelectionDialog.value = true
    }
    
    /**
     * Hide site selection dialog
     */
    fun hideSiteSelection() {
        _showSiteSelectionDialog.value = false
    }
    
    /**
     * Check if a site is selected
     */
    fun hasSiteSelected(): Boolean {
        return _selectedSite.value != null
    }
    
    /**
     * Get current site ID or -1 if none selected
     */
    fun getCurrentSiteId(): Long {
        return _selectedSiteId.value ?: -1L
    }
    
    /**
     * Update an existing site
     */
    fun updateSite(site: Site) {
        viewModelScope.launch {
            repository.updateSite(site)
            if (_selectedSiteId.value == site.id) {
                _selectedSite.postValue(site)
            }
        }
    }
    
    /**
     * Delete a site
     */
    fun deleteSite(site: Site) {
        viewModelScope.launch {
            repository.deleteSite(site)
            if (_selectedSiteId.value == site.id) {
                _selectedSite.postValue(null)
                _selectedSiteId.postValue(-1L)
            }
        }
    }
    
    /**
     * Clear selected site
     */
    fun clearSelection() {
        _selectedSite.value = null
        _selectedSiteId.value = -1L
    }
}
