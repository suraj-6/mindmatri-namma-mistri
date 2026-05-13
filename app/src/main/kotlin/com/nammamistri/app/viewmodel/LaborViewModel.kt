package com.nammamistri.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.nammamistri.app.data.db.AppDatabase
import com.nammamistri.app.data.model.WageEntry
import com.nammamistri.app.data.model.Worker
import com.nammamistri.app.data.repository.AppRepository
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel for Labor/Team Fragment
 * Manages workers, wage entries, attendance, and balance calculations
 */
class LaborViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: AppRepository
    
    // Current site ID
    private val _currentSiteId = MutableLiveData<Long>()
    
    // Workers with their balances for current site
    private val _workersWithBalances = MutableLiveData<List<WorkerWithBalance>>()
    val workersWithBalances: LiveData<List<WorkerWithBalance>> = _workersWithBalances
    
    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(database)
    }
    
    /**
     * Load workers for a specific site with their balances
     */
    fun loadWorkersBySite(siteId: Long) {
        _currentSiteId.value = siteId
        
        viewModelScope.launch {
            _isLoading.postValue(true)
            
            // Get workers for this site
            repository.getWorkersBySite(siteId).observeForever { workers ->
                viewModelScope.launch {
                    val workersWithBalances = workers.map { worker ->
                        computeWorkerBalance(worker)
                    }
                    _workersWithBalances.postValue(workersWithBalances)
                    _isLoading.postValue(false)
                }
            }
        }
    }
    
    /**
     * Compute balance for a worker
     * Balance = (Days Present × Daily Wage) - Total Advance
     */
    private suspend fun computeWorkerBalance(worker: Worker): WorkerWithBalance {
        val entries = repository.getEntriesByWorkerSync(worker.id)
        return computeBalanceDue(worker, entries)
    }
    
    /**
     * Calculate worker balance from entries
     */
    private fun computeBalanceDue(worker: Worker, entries: List<WageEntry>): WorkerWithBalance {
        val daysPresent = entries.count { it.isPresent }
        val totalEarned = daysPresent * worker.dailyWage
        val totalAdvance = entries.sumOf { it.advancePayment }
        val balanceDue = totalEarned - totalAdvance // negative = excess advance
        
        // Check if present today
        val todayStart = getTodayStartMillis()
        val todayEntry = entries.find { it.date == todayStart }
        val isPresentToday = todayEntry?.isPresent
        
        return WorkerWithBalance(
            worker = worker,
            daysPresent = daysPresent,
            totalEarned = totalEarned,
            totalAdvance = totalAdvance,
            balanceDue = balanceDue,
            isPresentToday = isPresentToday
        )
    }
    
    /**
     * Add a new worker
     */
    fun addWorker(worker: Worker) {
        viewModelScope.launch {
            repository.insertWorker(worker)
            // Refresh the list
            _currentSiteId.value?.let { loadWorkersBySite(it) }
        }
    }
    
    /**
     * Update a worker
     */
    fun updateWorker(worker: Worker) {
        viewModelScope.launch {
            repository.updateWorker(worker)
            _currentSiteId.value?.let { loadWorkersBySite(it) }
        }
    }
    
    /**
     * Delete a worker
     */
    fun deleteWorker(worker: Worker) {
        viewModelScope.launch {
            repository.deleteWorker(worker)
            _currentSiteId.value?.let { loadWorkersBySite(it) }
        }
    }
    
    /**
     * Mark attendance for a worker on a specific date
     * Prevents duplicate entries for same worker + date
     */
    fun markAttendance(workerId: Long, date: Long, isPresent: Boolean) {
        viewModelScope.launch {
            // Check if entry exists for this date
            val existingEntry = repository.getWageEntryByWorkerAndDate(workerId, date)
            
            if (existingEntry != null) {
                // Update existing entry
                val updatedEntry = existingEntry.copy(isPresent = isPresent)
                repository.updateWageEntry(updatedEntry)
            } else {
                // Create new entry
                val newEntry = WageEntry(
                    workerId = workerId,
                    date = date,
                    isPresent = isPresent,
                    advancePayment = 0.0,
                    notes = ""
                )
                repository.insertWageEntry(newEntry)
            }
            
            // Refresh the list
            _currentSiteId.value?.let { loadWorkersBySite(it) }
        }
    }
    
    /**
     * Add advance payment for a worker
     */
    fun addAdvance(workerId: Long, date: Long, amount: Double) {
        viewModelScope.launch {
            // Check if entry exists for this date
            val existingEntry = repository.getWageEntryByWorkerAndDate(workerId, date)
            
            if (existingEntry != null) {
                // Add to existing advance
                val updatedEntry = existingEntry.copy(
                    advancePayment = existingEntry.advancePayment + amount
                )
                repository.updateWageEntry(updatedEntry)
            } else {
                // Create new entry with advance only (not marking attendance)
                val newEntry = WageEntry(
                    workerId = workerId,
                    date = date,
                    isPresent = false,
                    advancePayment = amount,
                    notes = "Advance payment"
                )
                repository.insertWageEntry(newEntry)
            }
            
            // Refresh the list
            _currentSiteId.value?.let { loadWorkersBySite(it) }
        }
    }
    
    /**
     * Get balance for a specific worker
     */
    fun getWorkerBalance(worker: Worker, callback: (WorkerWithBalance) -> Unit) {
        viewModelScope.launch {
            val balance = computeWorkerBalance(worker)
            callback(balance)
        }
    }
    
    /**
     * Get wage entries for a worker
     */
    fun getWageEntries(workerId: Long): LiveData<List<WageEntry>> {
        return repository.getEntriesByWorker(workerId)
    }
    
    private fun getTodayStartMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}

/**
 * Worker with computed balance information
 */
data class WorkerWithBalance(
    val worker: Worker,
    val daysPresent: Int,
    val totalEarned: Double,
    val totalAdvance: Double,
    val balanceDue: Double, // positive = owed to worker, negative = excess advance
    val isPresentToday: Boolean? // null if not marked yet
)
