package com.nammamistri.app.data.repository

import com.nammamistri.app.data.db.CalculationDao
import com.nammamistri.app.data.model.Calculation
import com.nammamistri.app.data.model.CalculationType
import com.nammamistri.app.data.model.Material
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Calculation data operations
 * Acts as a single source of truth for calculation data
 */
class CalculationRepository(private val calculationDao: CalculationDao) {
    
    val allCalculations: Flow<List<Calculation>> = calculationDao.getAllCalculations()
    
    val allProjectNames: Flow<List<String>> = calculationDao.getAllProjectNames()
    
    suspend fun insert(calculation: Calculation): Long {
        return calculationDao.insertCalculation(calculation)
    }
    
    suspend fun insertWithMaterials(calculation: Calculation, materials: List<Material>): Long {
        return calculationDao.insertCalculationWithMaterials(calculation, materials)
    }
    
    suspend fun update(calculation: Calculation) {
        calculationDao.updateCalculation(calculation)
    }
    
    suspend fun delete(calculation: Calculation) {
        calculationDao.deleteMaterialsByCalculationId(calculation.id)
        calculationDao.deleteCalculation(calculation)
    }
    
    suspend fun deleteById(calculationId: Long) {
        calculationDao.deleteMaterialsByCalculationId(calculationId)
        calculationDao.deleteCalculationById(calculationId)
    }
    
    suspend fun getById(id: Long): Calculation? {
        return calculationDao.getCalculationById(id)
    }
    
    fun getByIdLiveData(id: Long) = calculationDao.getCalculationByIdLiveData(id)
    
    fun getByProject(projectName: String): Flow<List<Calculation>> {
        return calculationDao.getCalculationsByProject(projectName)
    }
    
    fun getByType(type: CalculationType): Flow<List<Calculation>> {
        return calculationDao.getCalculationsByType(type)
    }
    
    fun getMaterials(calculationId: Long): Flow<List<Material>> {
        return calculationDao.getMaterialsByCalculationId(calculationId)
    }
    
    suspend fun getTotalCostByProject(projectName: String): Double {
        return calculationDao.getTotalCostByProject(projectName) ?: 0.0
    }
    
    suspend fun getCalculationCount(): Int {
        return calculationDao.getCalculationCount()
    }
}
