package com.nammamistri.app.ui.rates

import androidx.lifecycle.*
import com.nammamistri.app.data.db.RatesDao
import com.nammamistri.app.data.model.RateCategory
import com.nammamistri.app.data.model.StandardRate
import com.nammamistri.app.data.repository.RatesRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Rates Fragment
 */
class RatesViewModel(
    private val ratesDao: RatesDao
) : ViewModel() {
    
    private val repository = RatesRepository(ratesDao)
    
    private val _selectedCategory = MutableLiveData<RateCategory?>(null)
    private val _filterType = MutableLiveData(FilterType.ALL)
    private val _searchQuery = MutableLiveData<String?>(null)
    
    val rates: LiveData<List<StandardRate>> = MediatorLiveData<List<StandardRate>>().apply {
        addSource(_selectedCategory) { updateRates() }
        addSource(_filterType) { updateRates() }
        addSource(_searchQuery) { updateRates() }
    }
    
    private val _ratesData = MutableLiveData<List<StandardRate>>()
    
    init {
        // Load all rates initially
        viewModelScope.launch {
            repository.allRates.collect { rates ->
                _ratesData.postValue(rates)
                (this@RatesViewModel.rates as MediatorLiveData).value = filterRates(rates)
            }
        }
    }
    
    private fun updateRates() {
        _ratesData.value?.let { allRates ->
            (rates as MediatorLiveData).value = filterRates(allRates)
        }
    }
    
    private fun filterRates(allRates: List<StandardRate>): List<StandardRate> {
        var filtered = allRates
        
        // Filter by search query
        _searchQuery.value?.let { query ->
            if (query.isNotBlank()) {
                filtered = filtered.filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.brand.contains(query, ignoreCase = true)
                }
            }
        }
        
        // Filter by category
        _selectedCategory.value?.let { category ->
            filtered = filtered.filter { it.category == category }
        }
        
        // Filter by type
        when (_filterType.value) {
            FilterType.MATERIALS -> {
                filtered = filtered.filter {
                    it.category in listOf(
                        RateCategory.CEMENT, RateCategory.SAND, RateCategory.AGGREGATE,
                        RateCategory.BRICK, RateCategory.STEEL, RateCategory.TILES,
                        RateCategory.PAINT, RateCategory.WOOD, RateCategory.ELECTRICAL,
                        RateCategory.PLUMBING, RateCategory.HARDWARE
                    )
                }
            }
            FilterType.LABOR -> {
                filtered = filtered.filter {
                    it.category in listOf(
                        RateCategory.LABOR_MASON, RateCategory.LABOR_HELPER,
                        RateCategory.LABOR_CARPENTER, RateCategory.LABOR_PLUMBER,
                        RateCategory.LABOR_ELECTRICIAN, RateCategory.LABOR_PAINTER
                    )
                }
            }
            FilterType.WORK -> {
                filtered = filtered.filter {
                    it.category in listOf(
                        RateCategory.WORK_BRICKWORK, RateCategory.WORK_PLASTERING,
                        RateCategory.WORK_FLOORING, RateCategory.WORK_PAINTING,
                        RateCategory.WORK_TILING
                    )
                }
            }
            else -> { /* No filter */ }
        }
        
        return filtered.sortedBy { it.category.ordinal }
    }
    
    fun filterByCategory(category: RateCategory?) {
        _selectedCategory.value = category
    }
    
    fun setFilterType(type: FilterType) {
        _filterType.value = type
        _selectedCategory.value = null // Reset category filter
    }
    
    fun search(query: String) {
        _searchQuery.value = query
    }
    
    fun addRate(rate: StandardRate) {
        viewModelScope.launch {
            repository.insertRate(rate)
        }
    }
    
    fun updateRate(rate: StandardRate, oldRateValue: Double) {
        viewModelScope.launch {
            repository.updateRateWithHistory(rate, oldRateValue)
        }
    }
    
    fun deleteRate(rate: StandardRate) {
        viewModelScope.launch {
            repository.deleteRate(rate)
        }
    }
    
    fun getRatesByCategory(category: RateCategory): LiveData<List<StandardRate>> {
        return repository.getRatesByCategoryLiveData(category)
    }
}

enum class FilterType {
    ALL, MATERIALS, LABOR, WORK
}

/**
 * ViewModelFactory for RatesViewModel
 */
class RatesViewModelFactory(
    private val ratesDao: RatesDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RatesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RatesViewModel(ratesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
