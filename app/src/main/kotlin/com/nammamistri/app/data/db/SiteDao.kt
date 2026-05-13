package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.Site

/**
 * Data Access Object for Site operations
 */
@Dao
interface SiteDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSite(site: Site): Long
    
    @Update
    suspend fun updateSite(site: Site)
    
    @Delete
    suspend fun deleteSite(site: Site)
    
    @Query("SELECT * FROM sites WHERE isActive = 1 ORDER BY startDate DESC")
    fun getAllActiveSites(): LiveData<List<Site>>
    
    @Query("SELECT * FROM sites ORDER BY startDate DESC")
    fun getAllSites(): LiveData<List<Site>>
    
    @Query("SELECT * FROM sites WHERE id = :id")
    suspend fun getSiteById(id: Long): Site?
    
    @Query("SELECT * FROM sites WHERE id = :id")
    fun getSiteByIdLiveData(id: Long): LiveData<Site?>
}
