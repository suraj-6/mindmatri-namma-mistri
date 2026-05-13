package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.WageEntry

/**
 * Data Access Object for WageEntry operations
 */
@Dao
interface WageEntryDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(wageEntry: WageEntry): Long
    
    @Update
    suspend fun updateEntry(wageEntry: WageEntry)
    
    @Delete
    suspend fun deleteEntry(wageEntry: WageEntry)
    
    @Query("SELECT * FROM wage_entries WHERE workerId = :workerId ORDER BY date DESC")
    fun getEntriesByWorker(workerId: Long): LiveData<List<WageEntry>>
    
    @Query("SELECT * FROM wage_entries WHERE workerId = :workerId ORDER BY date DESC")
    suspend fun getEntriesByWorkerSync(workerId: Long): List<WageEntry>
    
    @Query("SELECT COALESCE(SUM(advancePayment), 0.0) FROM wage_entries WHERE workerId = :workerId")
    suspend fun getTotalAdvanceByWorker(workerId: Long): Double
    
    @Query("SELECT COUNT(*) FROM wage_entries WHERE workerId = :workerId AND isPresent = 1")
    suspend fun getTotalDaysPresent(workerId: Long): Int
    
    @Query("SELECT * FROM wage_entries WHERE workerId = :workerId AND date = :date LIMIT 1")
    suspend fun getEntryByWorkerAndDate(workerId: Long, date: Long): WageEntry?
    
    @Query("SELECT * FROM wage_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getEntriesInDateRange(startDate: Long, endDate: Long): LiveData<List<WageEntry>>
}
