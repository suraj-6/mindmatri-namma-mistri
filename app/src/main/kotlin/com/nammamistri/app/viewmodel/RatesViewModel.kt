package com.nammamistri.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nammamistri.app.data.db.AppDatabase
import com.nammamistri.app.data.model.MaterialRate
import com.nammamistri.app.data.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Rates Fragment
 * Manages material and labor rates
 */
class RatesViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: AppRepository
    
    // All material rates
    val allMaterialRates: LiveData<List<MaterialRate>>
    
    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(database)
        allMaterialRates = repository.allMaterialRates
    }
    
    /**
     * Insert a new rate
     */
    fun insertRate(rate: MaterialRate) {
        viewModelScope.launch {
            repository.insertMaterialRate(rate)
        }
    }
    
    /**
     * Update an existing rate with current timestamp
     */
    fun updateRate(rate: MaterialRate) {
        viewModelScope.launch {
            val updatedRate = rate.copy(lastUpdated = System.currentTimeMillis())
            repository.updateMaterialRate(updatedRate)
        }
    }
    
    /**
     * Delete a rate
     */
    fun deleteRate(rate: MaterialRate) {
        viewModelScope.launch {
            repository.deleteMaterialRate(rate)
        }
    }
    
    /**
     * Get rate by name for calculations
     */
    suspend fun getRateByName(name: String): MaterialRate? {
        return repository.getMaterialRateByName(name)
    }
    
    /**
     * Calculate total cost for materials
     * Uses rates from database
     */
    fun calculateCost(
        bricks: Int,
        cementBags: Int,
        sandLoads: Double,
        callback: (CostBreakdown) -> Unit
    ) {
        viewModelScope.launch {
            // Get rates from database (fallback to defaults if not found)
            val brickRate = repository.getMaterialRateByName("Brick")?.pricePerUnit 
                ?: repository.getMaterialRateByName("Red Bricks")?.pricePerUnit 
                ?: 7000.0
                
            val cementRate = repository.getMaterialRateByName("Cement (OPC 53)")?.pricePerUnit 
                ?: 380.0
                
            val sandRate = repository.getMaterialRateByName("M-Sand")?.pricePerUnit 
                ?: repository.getMaterialRateByName("River Sand")?.pricePerUnit 
                ?: 3200.0
            
            // Calculate costs
            // Bricks are priced per 1000
            val brickCost = (bricks / 1000.0) * brickRate
            
            // Cement bags
            val cementCost = cementBags * cementRate
            
            // Sand loads (already in loads, rate is per load)
            val sandCost = sandLoads * sandRate
            
            val totalCost = brickCost + cementCost + sandCost
            
            callback(
                CostBreakdown(
                    brickCost = brickCost,
                    cementCost = cementCost,
                    sandCost = sandCost,
                    totalCost = totalCost,
                    brickRate = brickRate,
                    cementRate = cementRate,
                    sandRate = sandRate
                )
            )
        }
    }
}

/**
 * Cost breakdown with individual rates
 */
data class CostBreakdown(
    val brickCost: Double,
    val cementCost: Double,
    val sandCost: Double,
    val totalCost: Double,
    val brickRate: Double,
    val cementRate: Double,
    val sandRate: Double
)
