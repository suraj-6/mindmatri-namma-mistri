package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.Worker

/**
 * Data Access Object for Worker operations
 */
@Dao
interface WorkerDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorker(worker: Worker): Long
    
    @Update
    suspend fun updateWorker(worker: Worker)
    
    @Delete
    suspend fun deleteWorker(worker: Worker)
    
    @Query("DELETE FROM workers WHERE id = :id")
    suspend fun deleteWorkerById(id: Long)
    
    @Query("SELECT * FROM workers WHERE siteId = :siteId ORDER BY name ASC")
    fun getWorkersBySite(siteId: Long): LiveData<List<Worker>>
    
    @Query("SELECT * FROM workers WHERE id = :id")
    suspend fun getWorkerById(id: Long): Worker?
    
    @Query("SELECT * FROM workers WHERE id = :id")
    fun getWorkerByIdLiveData(id: Long): LiveData<Worker?>
    
    @Query("SELECT COUNT(*) FROM workers WHERE siteId = :siteId")
    suspend fun getWorkerCountBySite(siteId: Long): Int
}
