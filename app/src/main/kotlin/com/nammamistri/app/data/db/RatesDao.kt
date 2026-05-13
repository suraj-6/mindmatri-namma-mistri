package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.RateCategory
import com.nammamistri.app.data.model.RateHistory
import com.nammamistri.app.data.model.StandardRate
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Standard Rates operations
 */
@Dao
interface RatesDao {
    
    // Rate CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: StandardRate): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<StandardRate>)
    
    @Update
    suspend fun updateRate(rate: StandardRate)
    
    @Delete
    suspend fun deleteRate(rate: StandardRate)
    
    @Query("DELETE FROM standard_rates WHERE id = :rateId")
    suspend fun deleteRateById(rateId: Long)
    
    @Query("SELECT * FROM standard_rates ORDER BY category, name ASC")
    fun getAllRates(): Flow<List<StandardRate>>
    
    @Query("SELECT * FROM standard_rates ORDER BY category, name ASC")
    fun getAllRatesLiveData(): LiveData<List<StandardRate>>
    
    @Query("SELECT * FROM standard_rates WHERE id = :id")
    suspend fun getRateById(id: Long): StandardRate?
    
    @Query("SELECT * FROM standard_rates WHERE id = :id")
    fun getRateByIdLiveData(id: Long): LiveData<StandardRate?>
    
    @Query("SELECT * FROM standard_rates WHERE category = :category ORDER BY name ASC")
    fun getRatesByCategory(category: RateCategory): Flow<List<StandardRate>>
    
    @Query("SELECT * FROM standard_rates WHERE category = :category ORDER BY name ASC")
    fun getRatesByCategoryLiveData(category: RateCategory): LiveData<List<StandardRate>>
    
    @Query("SELECT * FROM standard_rates WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchRates(query: String): Flow<List<StandardRate>>
    
    @Query("SELECT * FROM standard_rates WHERE isDefault = 1 ORDER BY category, name ASC")
    fun getDefaultRates(): Flow<List<StandardRate>>
    
    @Query("SELECT * FROM standard_rates WHERE location = :location ORDER BY category, name ASC")
    fun getRatesByLocation(location: String): Flow<List<StandardRate>>
    
    @Query("SELECT DISTINCT location FROM standard_rates ORDER BY location ASC")
    fun getAllLocations(): Flow<List<String>>
    
    @Query("SELECT DISTINCT category FROM standard_rates ORDER BY category ASC")
    fun getAllCategories(): Flow<List<RateCategory>>
    
    // Rate History
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRateHistory(history: RateHistory): Long
    
    @Query("SELECT * FROM rate_history WHERE rateId = :rateId ORDER BY changedAt DESC")
    fun getRateHistoryByRate(rateId: Long): Flow<List<RateHistory>>
    
    @Query("SELECT * FROM rate_history ORDER BY changedAt DESC LIMIT 50")
    fun getRecentRateChanges(): Flow<List<RateHistory>>
    
    /**
     * Update rate and record history
     */
    @Transaction
    suspend fun updateRateWithHistory(rate: StandardRate, oldRate: Double, reason: String = "") {
        // Insert history record
        insertRateHistory(
            RateHistory(
                rateId = rate.id,
                oldRate = oldRate,
                newRate = rate.rate,
                reason = reason
            )
        )
        // Update the rate
        updateRate(rate.copy(updatedAt = System.currentTimeMillis()))
    }
    
    @Query("SELECT COUNT(*) FROM standard_rates")
    suspend fun getRateCount(): Int
}
