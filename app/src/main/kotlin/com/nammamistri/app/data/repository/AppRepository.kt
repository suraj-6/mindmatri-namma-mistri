package com.nammamistri.app.data.repository

import androidx.lifecycle.LiveData
import com.nammamistri.app.data.db.AppDatabase
import com.nammamistri.app.data.model.*

/**
 * Repository that provides access to all DAOs
 * Single source of truth for all data operations
 */
class AppRepository(private val database: AppDatabase) {
    
    // DAOs
    private val siteDao = database.siteDao()
    private val materialLogDao = database.materialLogDao()
    private val workerDao = database.workerDao()
    private val wageEntryDao = database.wageEntryDao()
    private val materialRateDao = database.materialRateDao()
    
    // ========================
    // SITE OPERATIONS
    // ========================
    
    val allActiveSites: LiveData<List<Site>> = siteDao.getAllActiveSites()
    val allSites: LiveData<List<Site>> = siteDao.getAllSites()
    
    suspend fun insertSite(site: Site): Long {
        return siteDao.insertSite(site)
    }
    
    suspend fun updateSite(site: Site) {
        siteDao.updateSite(site)
    }
    
    suspend fun deleteSite(site: Site) {
        siteDao.deleteSite(site)
    }
    
    suspend fun getSiteById(id: Long): Site? {
        return siteDao.getSiteById(id)
    }
    
    fun getSiteByIdLiveData(id: Long): LiveData<Site?> {
        return siteDao.getSiteByIdLiveData(id)
    }
    
    // ========================
    // MATERIAL LOG OPERATIONS
    // ========================
    
    suspend fun insertMaterialLog(materialLog: MaterialLog): Long {
        return materialLogDao.insert(materialLog)
    }
    
    suspend fun deleteMaterialLog(materialLog: MaterialLog) {
        materialLogDao.delete(materialLog)
    }
    
    suspend fun deleteMaterialLogById(id: Long) {
        materialLogDao.deleteById(id)
    }
    
    fun getLogsBySite(siteId: Long): LiveData<List<MaterialLog>> {
        return materialLogDao.getLogsBySite(siteId)
    }
    
    suspend fun getMaterialLogById(id: Long): MaterialLog? {
        return materialLogDao.getLogById(id)
    }
    
    suspend fun getTotalBricksBySite(siteId: Long): Int {
        return materialLogDao.getTotalBricksBySite(siteId) ?: 0
    }
    
    suspend fun getTotalCementBagsBySite(siteId: Long): Int {
        return materialLogDao.getTotalCementBagsBySite(siteId) ?: 0
    }
    
    suspend fun getTotalSandLoadsBySite(siteId: Long): Double {
        return materialLogDao.getTotalSandLoadsBySite(siteId) ?: 0.0
    }
    
    // ========================
    // WORKER OPERATIONS
    // ========================
    
    suspend fun insertWorker(worker: Worker): Long {
        return workerDao.insertWorker(worker)
    }
    
    suspend fun updateWorker(worker: Worker) {
        workerDao.updateWorker(worker)
    }
    
    suspend fun deleteWorker(worker: Worker) {
        workerDao.deleteWorker(worker)
    }
    
    suspend fun deleteWorkerById(id: Long) {
        workerDao.deleteWorkerById(id)
    }
    
    fun getWorkersBySite(siteId: Long): LiveData<List<Worker>> {
        return workerDao.getWorkersBySite(siteId)
    }
    
    suspend fun getWorkerById(id: Long): Worker? {
        return workerDao.getWorkerById(id)
    }
    
    fun getWorkerByIdLiveData(id: Long): LiveData<Worker?> {
        return workerDao.getWorkerByIdLiveData(id)
    }
    
    suspend fun getWorkerCountBySite(siteId: Long): Int {
        return workerDao.getWorkerCountBySite(siteId)
    }
    
    // ========================
    // WAGE ENTRY OPERATIONS
    // ========================
    
    suspend fun insertWageEntry(wageEntry: WageEntry): Long {
        return wageEntryDao.insertEntry(wageEntry)
    }
    
    suspend fun updateWageEntry(wageEntry: WageEntry) {
        wageEntryDao.updateEntry(wageEntry)
    }
    
    suspend fun deleteWageEntry(wageEntry: WageEntry) {
        wageEntryDao.deleteEntry(wageEntry)
    }
    
    fun getEntriesByWorker(workerId: Long): LiveData<List<WageEntry>> {
        return wageEntryDao.getEntriesByWorker(workerId)
    }
    
    suspend fun getEntriesByWorkerSync(workerId: Long): List<WageEntry> {
        return wageEntryDao.getEntriesByWorkerSync(workerId)
    }
    
    suspend fun getTotalAdvanceByWorker(workerId: Long): Double {
        return wageEntryDao.getTotalAdvanceByWorker(workerId)
    }
    
    suspend fun getTotalDaysPresent(workerId: Long): Int {
        return wageEntryDao.getTotalDaysPresent(workerId)
    }
    
    suspend fun getWageEntryByWorkerAndDate(workerId: Long, date: Long): WageEntry? {
        return wageEntryDao.getEntryByWorkerAndDate(workerId, date)
    }
    
    fun getWageEntriesInDateRange(startDate: Long, endDate: Long): LiveData<List<WageEntry>> {
        return wageEntryDao.getEntriesInDateRange(startDate, endDate)
    }
    
    // ========================
    // MATERIAL RATE OPERATIONS
    // ========================
    
    val allMaterialRates: LiveData<List<MaterialRate>> = materialRateDao.getAllRates()
    
    suspend fun insertMaterialRate(materialRate: MaterialRate): Long {
        return materialRateDao.insert(materialRate)
    }
    
    suspend fun insertAllMaterialRates(rates: List<MaterialRate>) {
        materialRateDao.insertAll(rates)
    }
    
    suspend fun updateMaterialRate(materialRate: MaterialRate) {
        materialRateDao.update(materialRate)
    }
    
    suspend fun deleteMaterialRate(materialRate: MaterialRate) {
        materialRateDao.delete(materialRate)
    }
    
    suspend fun deleteMaterialRateById(id: Long) {
        materialRateDao.deleteById(id)
    }
    
    suspend fun getMaterialRateById(id: Long): MaterialRate? {
        return materialRateDao.getRateById(id)
    }
    
    suspend fun getMaterialRateByName(name: String): MaterialRate? {
        return materialRateDao.getRateByName(name)
    }
    
    suspend fun getMaterialRateCount(): Int {
        return materialRateDao.getRateCount()
    }
    
    // ========================
    // COMPUTED VALUES
    // ========================
    
    /**
     * Calculate balance due for a worker
     * Balance = (Days Present * Daily Wage) - Total Advance
     */
    suspend fun calculateBalanceDue(workerId: Long, dailyWage: Double): Double {
        val daysPresent = getTotalDaysPresent(workerId)
        val totalAdvance = getTotalAdvanceByWorker(workerId)
        return (daysPresent * dailyWage) - totalAdvance
    }
}
