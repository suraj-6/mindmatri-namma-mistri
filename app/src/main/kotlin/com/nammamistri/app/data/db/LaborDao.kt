package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Labor/Team operations
 */
@Dao
interface LaborDao {
    
    // Labor CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabor(labor: Labor): Long
    
    @Update
    suspend fun updateLabor(labor: Labor)
    
    @Delete
    suspend fun deleteLabor(labor: Labor)
    
    @Query("SELECT * FROM laborers WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveLaborers(): Flow<List<Labor>>
    
    @Query("SELECT * FROM laborers ORDER BY name ASC")
    fun getAllLaborers(): Flow<List<Labor>>
    
    @Query("SELECT * FROM laborers ORDER BY name ASC")
    fun getAllLaborersLiveData(): LiveData<List<Labor>>
    
    @Query("SELECT * FROM laborers WHERE id = :id")
    suspend fun getLaborById(id: Long): Labor?
    
    @Query("SELECT * FROM laborers WHERE id = :id")
    fun getLaborByIdLiveData(id: Long): LiveData<Labor?>
    
    @Query("SELECT * FROM laborers WHERE skill = :skill AND isActive = 1")
    fun getLaborersBySkill(skill: LaborSkill): Flow<List<Labor>>
    
    @Query("SELECT * FROM laborers WHERE name LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%'")
    fun searchLaborers(query: String): Flow<List<Labor>>
    
    // Attendance CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendances(attendances: List<Attendance>)
    
    @Update
    suspend fun updateAttendance(attendance: Attendance)
    
    @Delete
    suspend fun deleteAttendance(attendance: Attendance)
    
    @Query("SELECT * FROM attendance WHERE laborId = :laborId ORDER BY date DESC")
    fun getAttendanceByLabor(laborId: Long): Flow<List<Attendance>>
    
    @Query("SELECT * FROM attendance WHERE date = :date ORDER BY laborId ASC")
    fun getAttendanceByDate(date: Long): Flow<List<Attendance>>
    
    @Query("SELECT * FROM attendance WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getAttendanceInRange(startDate: Long, endDate: Long): Flow<List<Attendance>>
    
    @Query("SELECT * FROM attendance WHERE laborId = :laborId AND date = :date LIMIT 1")
    suspend fun getAttendanceForLaborOnDate(laborId: Long, date: Long): Attendance?
    
    @Query("SELECT SUM(wageEarned) FROM attendance WHERE laborId = :laborId")
    suspend fun getTotalWagesEarnedByLabor(laborId: Long): Double?
    
    @Query("SELECT SUM(wageEarned) FROM attendance WHERE laborId = :laborId AND date BETWEEN :startDate AND :endDate")
    suspend fun getWagesInRange(laborId: Long, startDate: Long, endDate: Long): Double?
    
    @Query("SELECT COUNT(*) FROM attendance WHERE laborId = :laborId AND status = :status")
    suspend fun getAttendanceCountByStatus(laborId: Long, status: AttendanceStatus): Int
    
    // Payment CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment): Long
    
    @Update
    suspend fun updatePayment(payment: Payment)
    
    @Delete
    suspend fun deletePayment(payment: Payment)
    
    @Query("SELECT * FROM payments WHERE laborId = :laborId ORDER BY paymentDate DESC")
    fun getPaymentsByLabor(laborId: Long): Flow<List<Payment>>
    
    @Query("SELECT SUM(amount) FROM payments WHERE laborId = :laborId")
    suspend fun getTotalPaymentsByLabor(laborId: Long): Double?
    
    @Query("SELECT SUM(amount) FROM payments WHERE laborId = :laborId AND isAdvance = 1")
    suspend fun getTotalAdvanceByLabor(laborId: Long): Double?
    
    // Calculate balance (wages earned - payments made)
    @Query("""
        SELECT 
            COALESCE((SELECT SUM(wageEarned) FROM attendance WHERE laborId = :laborId), 0) -
            COALESCE((SELECT SUM(amount) FROM payments WHERE laborId = :laborId), 0)
        AS balance
    """)
    suspend fun getLaborBalance(laborId: Long): Double
}
