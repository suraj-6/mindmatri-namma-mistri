package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.MaterialRate

/**
 * Data Access Object for MaterialRate operations
 */
@Dao
interface MaterialRateDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(materialRate: MaterialRate): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rates: List<MaterialRate>)
    
    @Update
    suspend fun update(materialRate: MaterialRate)
    
    @Delete
    suspend fun delete(materialRate: MaterialRate)
    
    @Query("DELETE FROM material_rates WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    @Query("SELECT * FROM material_rates ORDER BY materialName ASC")
    fun getAllRates(): LiveData<List<MaterialRate>>
    
    @Query("SELECT * FROM material_rates WHERE id = :id")
    suspend fun getRateById(id: Long): MaterialRate?
    
    @Query("SELECT * FROM material_rates WHERE materialName = :name LIMIT 1")
    suspend fun getRateByName(name: String): MaterialRate?
    
    @Query("SELECT COUNT(*) FROM material_rates")
    suspend fun getRateCount(): Int
}
