package com.nammamistri.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamistri.app.data.model.PhotoCategory
import com.nammamistri.app.data.model.Project
import com.nammamistri.app.data.model.ProjectStatus
import com.nammamistri.app.data.model.SitePhoto
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Site Photos operations
 */
@Dao
interface PhotoDao {
    
    // Photo CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: SitePhoto): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<SitePhoto>)
    
    @Update
    suspend fun updatePhoto(photo: SitePhoto)
    
    @Delete
    suspend fun deletePhoto(photo: SitePhoto)
    
    @Query("DELETE FROM site_photos WHERE id = :photoId")
    suspend fun deletePhotoById(photoId: Long)
    
    @Query("SELECT * FROM site_photos ORDER BY takenAt DESC")
    fun getAllPhotos(): Flow<List<SitePhoto>>
    
    @Query("SELECT * FROM site_photos ORDER BY takenAt DESC")
    fun getAllPhotosLiveData(): LiveData<List<SitePhoto>>
    
    @Query("SELECT * FROM site_photos WHERE id = :id")
    suspend fun getPhotoById(id: Long): SitePhoto?
    
    @Query("SELECT * FROM site_photos WHERE projectName = :projectName ORDER BY takenAt DESC")
    fun getPhotosByProject(projectName: String): Flow<List<SitePhoto>>
    
    @Query("SELECT * FROM site_photos WHERE category = :category ORDER BY takenAt DESC")
    fun getPhotosByCategory(category: PhotoCategory): Flow<List<SitePhoto>>
    
    @Query("SELECT * FROM site_photos WHERE projectName = :projectName AND category = :category ORDER BY takenAt DESC")
    fun getPhotosByProjectAndCategory(projectName: String, category: PhotoCategory): Flow<List<SitePhoto>>
    
    @Query("SELECT * FROM site_photos WHERE takenAt BETWEEN :startDate AND :endDate ORDER BY takenAt DESC")
    fun getPhotosInDateRange(startDate: Long, endDate: Long): Flow<List<SitePhoto>>
    
    @Query("SELECT * FROM site_photos WHERE description LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%'")
    fun searchPhotos(query: String): Flow<List<SitePhoto>>
    
    @Query("SELECT * FROM site_photos WHERE isSynced = 0")
    suspend fun getUnsyncedPhotos(): List<SitePhoto>
    
    @Query("UPDATE site_photos SET isSynced = 1 WHERE id = :photoId")
    suspend fun markPhotoAsSynced(photoId: Long)
    
    @Query("SELECT DISTINCT projectName FROM site_photos ORDER BY projectName ASC")
    fun getAllProjectNamesFromPhotos(): Flow<List<String>>
    
    @Query("SELECT COUNT(*) FROM site_photos WHERE projectName = :projectName")
    suspend fun getPhotoCountByProject(projectName: String): Int
    
    // Project CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project): Long
    
    @Update
    suspend fun updateProject(project: Project)
    
    @Delete
    suspend fun deleteProject(project: Project)
    
    @Query("SELECT * FROM projects ORDER BY createdAt DESC")
    fun getAllProjects(): Flow<List<Project>>
    
    @Query("SELECT * FROM projects ORDER BY createdAt DESC")
    fun getAllProjectsLiveData(): LiveData<List<Project>>
    
    @Query("SELECT * FROM projects WHERE id = :id")
    suspend fun getProjectById(id: Long): Project?
    
    @Query("SELECT * FROM projects WHERE status = :status ORDER BY createdAt DESC")
    fun getProjectsByStatus(status: ProjectStatus): Flow<List<Project>>
    
    @Query("SELECT * FROM projects WHERE name LIKE '%' || :query || '%' OR clientName LIKE '%' || :query || '%'")
    fun searchProjects(query: String): Flow<List<Project>>
}
