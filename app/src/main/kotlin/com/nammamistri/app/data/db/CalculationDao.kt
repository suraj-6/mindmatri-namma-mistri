package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.Calculation
import com.nammamistri.app.data.model.CalculationType
import com.nammamistri.app.data.model.Material
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Calculation operations
 */
@Dao
interface CalculationDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculation(calculation: Calculation): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterials(materials: List<Material>)
    
    @Update
    suspend fun updateCalculation(calculation: Calculation)
    
    @Delete
    suspend fun deleteCalculation(calculation: Calculation)
    
    @Query("DELETE FROM calculations WHERE id = :calculationId")
    suspend fun deleteCalculationById(calculationId: Long)
    
    @Query("DELETE FROM materials WHERE calculationId = :calculationId")
    suspend fun deleteMaterialsByCalculationId(calculationId: Long)
    
    @Query("SELECT * FROM calculations ORDER BY createdAt DESC")
    fun getAllCalculations(): Flow<List<Calculation>>
    
    @Query("SELECT * FROM calculations ORDER BY createdAt DESC")
    fun getAllCalculationsLiveData(): LiveData<List<Calculation>>
    
    @Query("SELECT * FROM calculations WHERE id = :id")
    suspend fun getCalculationById(id: Long): Calculation?
    
    @Query("SELECT * FROM calculations WHERE id = :id")
    fun getCalculationByIdLiveData(id: Long): LiveData<Calculation?>
    
    @Query("SELECT * FROM calculations WHERE projectName = :projectName ORDER BY createdAt DESC")
    fun getCalculationsByProject(projectName: String): Flow<List<Calculation>>
    
    @Query("SELECT * FROM calculations WHERE calculationType = :type ORDER BY createdAt DESC")
    fun getCalculationsByType(type: CalculationType): Flow<List<Calculation>>
    
    @Query("SELECT * FROM materials WHERE calculationId = :calculationId")
    fun getMaterialsByCalculationId(calculationId: Long): Flow<List<Material>>
    
    @Query("SELECT SUM(totalCost) FROM calculations WHERE projectName = :projectName")
    suspend fun getTotalCostByProject(projectName: String): Double?
    
    @Query("SELECT DISTINCT projectName FROM calculations ORDER BY projectName ASC")
    fun getAllProjectNames(): Flow<List<String>>
    
    @Query("SELECT COUNT(*) FROM calculations")
    suspend fun getCalculationCount(): Int
    
    @Transaction
    suspend fun insertCalculationWithMaterials(
        calculation: Calculation,
        materials: List<Material>
    ): Long {
        val calcId = insertCalculation(calculation)
        val materialsWithId = materials.map { it.copy(calculationId = calcId) }
        insertMaterials(materialsWithId)
        return calcId
    }
}
