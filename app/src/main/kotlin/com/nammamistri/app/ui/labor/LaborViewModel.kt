package com.nammamistri.app.ui.labor

import androidx.lifecycle.*
import com.nammamistri.app.data.db.LaborDao
import com.nammamistri.app.data.model.*
import com.nammamistri.app.data.repository.LaborRepository
import com.nammamistri.app.utils.startOfDay
import com.nammamistri.app.utils.todayStart
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * ViewModel for Labor Fragment
 */
class LaborViewModel(
    private val laborDao: LaborDao
) : ViewModel() {
    
    private val repository = LaborRepository(laborDao)
    
    private val _selectedSkill = MutableLiveData<LaborSkill?>(null)
    private val _selectedDate = MutableLiveData(todayStart())
    
    val laborers: LiveData<List<Labor>> = _selectedSkill.switchMap { skill ->
        if (skill == null) {
            repository.allActiveLaborers.asLiveData()
        } else {
            repository.getLaborersBySkill(skill).asLiveData()
        }
    }
    
    val todaySummary: LiveData<DaySummary> = _selectedDate.switchMap { date ->
        repository.getAttendanceByDate(date.startOfDay()).asLiveData().map { attendances ->
            DaySummary(
                date = date,
                presentCount = attendances.count { it.status == AttendanceStatus.PRESENT },
                halfDayCount = attendances.count { it.status == AttendanceStatus.HALF_DAY },
                absentCount = attendances.count { it.status == AttendanceStatus.ABSENT },
                totalWages = attendances.sumOf { it.wageEarned }
            )
        }
    }
    
    fun filterBySkill(skill: LaborSkill?) {
        _selectedSkill.value = skill
    }
    
    fun setSelectedDate(date: Long) {
        _selectedDate.value = date.startOfDay()
    }
    
    fun addLabor(labor: Labor) {
        viewModelScope.launch {
            repository.insertLabor(labor)
        }
    }
    
    fun updateLabor(labor: Labor) {
        viewModelScope.launch {
            repository.updateLabor(labor)
        }
    }
    
    fun deleteLabor(labor: Labor) {
        viewModelScope.launch {
            repository.deleteLabor(labor)
        }
    }
    
    fun markAttendance(
        laborId: Long,
        projectName: String,
        hoursWorked: Double,
        overtimeHours: Double,
        status: AttendanceStatus,
        wageEarned: Double,
        advancePaid: Double
    ) {
        viewModelScope.launch {
            val attendance = Attendance(
                laborId = laborId,
                date = _selectedDate.value ?: todayStart(),
                projectName = projectName,
                hoursWorked = hoursWorked,
                overtimeHours = overtimeHours,
                status = status,
                wageEarned = wageEarned,
                advancePaid = advancePaid
            )
            repository.insertAttendance(attendance)
        }
    }
    
    fun recordPayment(payment: Payment) {
        viewModelScope.launch {
            repository.insertPayment(payment)
        }
    }
    
    fun getLaborDetails(
        laborId: Long,
        callback: (totalWages: Double, totalPayments: Double, balance: Double) -> Unit
    ) {
        viewModelScope.launch {
            val totalWages = repository.getTotalWagesEarned(laborId)
            val totalPayments = repository.getTotalPayments(laborId)
            val balance = repository.getLaborBalance(laborId)
            callback(totalWages, totalPayments, balance)
        }
    }
    
    fun searchLaborers(query: String): LiveData<List<Labor>> {
        return repository.searchLaborers(query).asLiveData()
    }
}

/**
 * Summary data for a day
 */
data class DaySummary(
    val date: Long,
    val presentCount: Int,
    val halfDayCount: Int,
    val absentCount: Int,
    val totalWages: Double
)

/**
 * ViewModelFactory for LaborViewModel
 */
class LaborViewModelFactory(
    private val laborDao: LaborDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaborViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LaborViewModel(laborDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
