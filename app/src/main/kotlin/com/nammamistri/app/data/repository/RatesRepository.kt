package com.nammamistri.app.data.repository

import androidx.lifecycle.LiveData
import com.nammamistri.app.data.db.RatesDao
import com.nammamistri.app.data.model.RateCategory
import com.nammamistri.app.data.model.RateHistory
import com.nammamistri.app.data.model.StandardRate
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Standard Rates data operations
 */
class RatesRepository(private val ratesDao: RatesDao) {
    
    val allRates: Flow<List<StandardRate>> = ratesDao.getAllRates()
    val allRatesLiveData: LiveData<List<StandardRate>> = ratesDao.getAllRatesLiveData()
    val defaultRates: Flow<List<StandardRate>> = ratesDao.getDefaultRates()
    val allLocations: Flow<List<String>> = ratesDao.getAllLocations()
    val allCategories: Flow<List<RateCategory>> = ratesDao.getAllCategories()
    val recentRateChanges: Flow<List<RateHistory>> = ratesDao.getRecentRateChanges()
    
    suspend fun insertRate(rate: StandardRate): Long {
        return ratesDao.insertRate(rate)
    }
    
    suspend fun insertRates(rates: List<StandardRate>) {
        ratesDao.insertRates(rates)
    }
    
    suspend fun updateRate(rate: StandardRate) {
        ratesDao.updateRate(rate)
    }
    
    suspend fun updateRateWithHistory(rate: StandardRate, oldRate: Double, reason: String = "") {
        ratesDao.updateRateWithHistory(rate, oldRate, reason)
    }
    
    suspend fun deleteRate(rate: StandardRate) {
        ratesDao.deleteRate(rate)
    }
    
    suspend fun deleteRateById(rateId: Long) {
        ratesDao.deleteRateById(rateId)
    }
    
    suspend fun getRateById(id: Long): StandardRate? {
        return ratesDao.getRateById(id)
    }
    
    fun getRateByIdLiveData(id: Long): LiveData<StandardRate?> {
        return ratesDao.getRateByIdLiveData(id)
    }
    
    fun getRatesByCategory(category: RateCategory): Flow<List<StandardRate>> {
        return ratesDao.getRatesByCategory(category)
    }
    
    fun getRatesByCategoryLiveData(category: RateCategory): LiveData<List<StandardRate>> {
        return ratesDao.getRatesByCategoryLiveData(category)
    }
    
    fun searchRates(query: String): Flow<List<StandardRate>> {
        return ratesDao.searchRates(query)
    }
    
    fun getRatesByLocation(location: String): Flow<List<StandardRate>> {
        return ratesDao.getRatesByLocation(location)
    }
    
    fun getRateHistory(rateId: Long): Flow<List<RateHistory>> {
        return ratesDao.getRateHistoryByRate(rateId)
    }
    
    suspend fun getRateCount(): Int {
        return ratesDao.getRateCount()
    }
}
