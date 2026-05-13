package com.nammamistri.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.nammamistri.app.data.db.AppDatabase
import com.nammamistri.app.data.model.MaterialLog
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.data.repository.AppRepository
import com.nammamistri.app.utils.ConstructionFormulas
import kotlinx.coroutines.launch

/**
 * ViewModel for Calculator Fragment
 * Handles material calculations and saves MaterialLogs to Room DB
 */
class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: AppRepository = AppRepository(AppDatabase.getDatabase(application))
    
    // All active sites for dropdown selection
    val allActiveSites: LiveData<List<Site>> = repository.allActiveSites
    
    // Currently selected site
    private val _currentSite = MutableLiveData<Site?>()
    val currentSite: LiveData<Site?> = _currentSite
    
    // Current site ID for fetching logs
    private val _currentSiteId = MutableLiveData<Long>()
    
    // Material logs for current site
    val materialLogs: LiveData<List<MaterialLog>> = _currentSiteId.switchMap { siteId ->
        if (siteId > 0) {
            repository.getLogsBySite(siteId)
        } else {
            MutableLiveData(emptyList())
        }
    }
    
    // Save status
    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> = _saveStatus
    
    /**
     * Set the current site for calculations
     */
    fun setCurrentSite(site: Site) {
        _currentSite.value = site
        _currentSiteId.value = site.id
    }
    
    /**
     * Create a new site
     */
    fun createSite(name: String, location: String, onComplete: (Long) -> Unit) {
        viewModelScope.launch {
            val site = Site(
                name = name,
                location = location,
                startDate = System.currentTimeMillis()
            )
            val siteId = repository.insertSite(site)
            val newSite = repository.getSiteById(siteId)
            newSite?.let { setCurrentSite(it) }
            onComplete(siteId)
        }
    }
    
    /**
     * Save a material log to the database
     */
    fun saveMaterialLog(materialLog: MaterialLog) {
        viewModelScope.launch {
            repository.insertMaterialLog(materialLog)
            _saveStatus.postValue(true)
        }
    }
    
    /**
     * Delete a material log
     */
    fun deleteMaterialLog(materialLog: MaterialLog) {
        viewModelScope.launch {
            repository.deleteMaterialLog(materialLog)
        }
    }
    
    /**
     * Delete material log by ID
     */
    fun deleteMaterialLogById(id: Long) {
        viewModelScope.launch {
            repository.deleteMaterialLogById(id)
        }
    }
    
    /**
     * Get site summary (total materials used)
     */
    fun getSiteSummary(siteId: Long, callback: (SiteSummary) -> Unit) {
        viewModelScope.launch {
            val totalBricks = repository.getTotalBricksBySite(siteId)
            val totalCement = repository.getTotalCementBagsBySite(siteId)
            val totalSand = repository.getTotalSandLoadsBySite(siteId)
            callback(SiteSummary(totalBricks, totalCement, totalSand))
        }
    }
    
    /**
     * Calculate materials using ConstructionFormulas
     */
    fun calculate(
        lengthM: Double,
        heightM: Double,
        thicknessInch: Double
    ): CalculationResult {
        val bricks = ConstructionFormulas.calculateBricks(lengthM, heightM, thicknessInch)
        val cementBags = ConstructionFormulas.calculateCement(lengthM, heightM, thicknessInch)
        val sandLoads = ConstructionFormulas.calculateSand(cementBags)
        
        return CalculationResult(
            bricks = bricks,
            cementBags = cementBags,
            sandLoads = sandLoads,
            wallLength = lengthM,
            wallHeight = heightM,
            wallThickness = thicknessInch
        )
    }
    
    /**
     * Reset save status
     */
    fun resetSaveStatus() {
        _saveStatus.value = false
    }
}

/**
 * Result of a brickwork calculation
 */
data class CalculationResult(
    val bricks: Int,
    val cementBags: Int,
    val sandLoads: Double,
    val wallLength: Double,
    val wallHeight: Double,
    val wallThickness: Double
)

/**
 * Summary of materials used at a site
 */
data class SiteSummary(
    val totalBricks: Int,
    val totalCementBags: Int,
    val totalSandLoads: Double
)
