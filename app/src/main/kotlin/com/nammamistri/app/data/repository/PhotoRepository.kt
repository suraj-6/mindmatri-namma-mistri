package com.nammamistri.app.data.repository

import androidx.lifecycle.LiveData
import com.nammamistri.app.data.db.PhotoDao
import com.nammamistri.app.data.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Site Photos data operations
 */
class PhotoRepository(private val photoDao: PhotoDao) {
    
    val allPhotos: Flow<List<SitePhoto>> = photoDao.getAllPhotos()
    val allPhotosLiveData: LiveData<List<SitePhoto>> = photoDao.getAllPhotosLiveData()
    val allProjects: Flow<List<Project>> = photoDao.getAllProjects()
    val allProjectsLiveData: LiveData<List<Project>> = photoDao.getAllProjectsLiveData()
    val allProjectNames: Flow<List<String>> = photoDao.getAllProjectNamesFromPhotos()
    
    // Photo operations
    suspend fun insertPhoto(photo: SitePhoto): Long {
        return photoDao.insertPhoto(photo)
    }
    
    suspend fun insertPhotos(photos: List<SitePhoto>) {
        photoDao.insertPhotos(photos)
    }
    
    suspend fun updatePhoto(photo: SitePhoto) {
        photoDao.updatePhoto(photo)
    }
    
    suspend fun deletePhoto(photo: SitePhoto) {
        photoDao.deletePhoto(photo)
    }
    
    suspend fun deletePhotoById(photoId: Long) {
        photoDao.deletePhotoById(photoId)
    }
    
    suspend fun getPhotoById(id: Long): SitePhoto? {
        return photoDao.getPhotoById(id)
    }
    
    fun getPhotosByProject(projectName: String): Flow<List<SitePhoto>> {
        return photoDao.getPhotosByProject(projectName)
    }
    
    fun getPhotosByCategory(category: PhotoCategory): Flow<List<SitePhoto>> {
        return photoDao.getPhotosByCategory(category)
    }
    
    fun getPhotosByProjectAndCategory(
        projectName: String, 
        category: PhotoCategory
    ): Flow<List<SitePhoto>> {
        return photoDao.getPhotosByProjectAndCategory(projectName, category)
    }
    
    fun getPhotosInDateRange(startDate: Long, endDate: Long): Flow<List<SitePhoto>> {
        return photoDao.getPhotosInDateRange(startDate, endDate)
    }
    
    fun searchPhotos(query: String): Flow<List<SitePhoto>> {
        return photoDao.searchPhotos(query)
    }
    
    suspend fun getUnsyncedPhotos(): List<SitePhoto> {
        return photoDao.getUnsyncedPhotos()
    }
    
    suspend fun markPhotoAsSynced(photoId: Long) {
        photoDao.markPhotoAsSynced(photoId)
    }
    
    suspend fun getPhotoCountByProject(projectName: String): Int {
        return photoDao.getPhotoCountByProject(projectName)
    }
    
    // Project operations
    suspend fun insertProject(project: Project): Long {
        return photoDao.insertProject(project)
    }
    
    suspend fun updateProject(project: Project) {
        photoDao.updateProject(project)
    }
    
    suspend fun deleteProject(project: Project) {
        photoDao.deleteProject(project)
    }
    
    suspend fun getProjectById(id: Long): Project? {
        return photoDao.getProjectById(id)
    }
    
    fun getProjectsByStatus(status: ProjectStatus): Flow<List<Project>> {
        return photoDao.getProjectsByStatus(status)
    }
    
    fun searchProjects(query: String): Flow<List<Project>> {
        return photoDao.searchProjects(query)
    }
}
