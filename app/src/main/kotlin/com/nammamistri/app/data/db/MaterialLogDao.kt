package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.MaterialLog

/**
 * Data Access Object for MaterialLog operations
 */
@Dao
interface MaterialLogDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(materialLog: MaterialLog): Long
    
    @Delete
    suspend fun delete(materialLog: MaterialLog)
    
    @Query("DELETE FROM material_logs WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    @Query("SELECT * FROM material_logs WHERE siteId = :siteId ORDER BY calculatedOn DESC")
    fun getLogsBySite(siteId: Long): LiveData<List<MaterialLog>>
    
    @Query("SELECT * FROM material_logs WHERE id = :id")
    suspend fun getLogById(id: Long): MaterialLog?
    
    @Query("SELECT SUM(bricks) FROM material_logs WHERE siteId = :siteId")
    suspend fun getTotalBricksBySite(siteId: Long): Int?
    
    @Query("SELECT SUM(cementBags) FROM material_logs WHERE siteId = :siteId")
    suspend fun getTotalCementBagsBySite(siteId: Long): Int?
    
    @Query("SELECT SUM(sandLoads) FROM material_logs WHERE siteId = :siteId")
    suspend fun getTotalSandLoadsBySite(siteId: Long): Double?
}
