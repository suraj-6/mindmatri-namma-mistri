package com.nammamistri.app.data.repository

import androidx.lifecycle.LiveData
import com.nammamistri.app.data.db.LaborDao
import com.nammamistri.app.data.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Labor/Team data operations
 */
class LaborRepository(private val laborDao: LaborDao) {
    
    val allLaborers: Flow<List<Labor>> = laborDao.getAllLaborers()
    val allActiveLaborers: Flow<List<Labor>> = laborDao.getAllActiveLaborers()
    val allLaborersLiveData: LiveData<List<Labor>> = laborDao.getAllLaborersLiveData()
    
    // Labor operations
    suspend fun insertLabor(labor: Labor): Long {
        return laborDao.insertLabor(labor)
    }
    
    suspend fun updateLabor(labor: Labor) {
        laborDao.updateLabor(labor)
    }
    
    suspend fun deleteLabor(labor: Labor) {
        laborDao.deleteLabor(labor)
    }
    
    suspend fun getLaborById(id: Long): Labor? {
        return laborDao.getLaborById(id)
    }
    
    fun getLaborByIdLiveData(id: Long): LiveData<Labor?> {
        return laborDao.getLaborByIdLiveData(id)
    }
    
    fun getLaborersBySkill(skill: LaborSkill): Flow<List<Labor>> {
        return laborDao.getLaborersBySkill(skill)
    }
    
    fun searchLaborers(query: String): Flow<List<Labor>> {
        return laborDao.searchLaborers(query)
    }
    
    // Attendance operations
    suspend fun insertAttendance(attendance: Attendance): Long {
        return laborDao.insertAttendance(attendance)
    }
    
    suspend fun insertAttendances(attendances: List<Attendance>) {
        laborDao.insertAttendances(attendances)
    }
    
    suspend fun updateAttendance(attendance: Attendance) {
        laborDao.updateAttendance(attendance)
    }
    
    suspend fun deleteAttendance(attendance: Attendance) {
        laborDao.deleteAttendance(attendance)
    }
    
    fun getAttendanceByLabor(laborId: Long): Flow<List<Attendance>> {
        return laborDao.getAttendanceByLabor(laborId)
    }
    
    fun getAttendanceByDate(date: Long): Flow<List<Attendance>> {
        return laborDao.getAttendanceByDate(date)
    }
    
    fun getAttendanceInRange(startDate: Long, endDate: Long): Flow<List<Attendance>> {
        return laborDao.getAttendanceInRange(startDate, endDate)
    }
    
    suspend fun getAttendanceForLaborOnDate(laborId: Long, date: Long): Attendance? {
        return laborDao.getAttendanceForLaborOnDate(laborId, date)
    }
    
    suspend fun getTotalWagesEarned(laborId: Long): Double {
        return laborDao.getTotalWagesEarnedByLabor(laborId) ?: 0.0
    }
    
    suspend fun getWagesInRange(laborId: Long, startDate: Long, endDate: Long): Double {
        return laborDao.getWagesInRange(laborId, startDate, endDate) ?: 0.0
    }
    
    suspend fun getAttendanceCount(laborId: Long, status: AttendanceStatus): Int {
        return laborDao.getAttendanceCountByStatus(laborId, status)
    }
    
    // Payment operations
    suspend fun insertPayment(payment: Payment): Long {
        return laborDao.insertPayment(payment)
    }
    
    suspend fun updatePayment(payment: Payment) {
        laborDao.updatePayment(payment)
    }
    
    suspend fun deletePayment(payment: Payment) {
        laborDao.deletePayment(payment)
    }
    
    fun getPaymentsByLabor(laborId: Long): Flow<List<Payment>> {
        return laborDao.getPaymentsByLabor(laborId)
    }
    
    suspend fun getTotalPayments(laborId: Long): Double {
        return laborDao.getTotalPaymentsByLabor(laborId) ?: 0.0
    }
    
    suspend fun getTotalAdvance(laborId: Long): Double {
        return laborDao.getTotalAdvanceByLabor(laborId) ?: 0.0
    }
    
    suspend fun getLaborBalance(laborId: Long): Double {
        return laborDao.getLaborBalance(laborId)
    }
}
