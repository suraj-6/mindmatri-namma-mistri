package com.nammamistri.app.ui.photos

import androidx.lifecycle.*
import com.nammamistri.app.data.db.PhotoDao
import com.nammamistri.app.data.model.PhotoCategory
import com.nammamistri.app.data.model.Project
import com.nammamistri.app.data.model.SitePhoto
import com.nammamistri.app.data.repository.PhotoRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Photos Fragment
 */
class PhotosViewModel(
    private val photoDao: PhotoDao
) : ViewModel() {
    
    private val repository = PhotoRepository(photoDao)
    
    private val _selectedCategory = MutableLiveData<PhotoCategory?>(null)
    private val _selectedProject = MutableLiveData<String?>(null)
    
    val photos: LiveData<List<SitePhoto>> = _selectedCategory.switchMap { category ->
        if (category == null) {
            repository.allPhotos.asLiveData()
        } else {
            repository.getPhotosByCategory(category).asLiveData()
        }
    }
    
    val projectNames: LiveData<List<String>> = repository.allProjectNames.asLiveData()
    
    val projects: LiveData<List<Project>> = repository.allProjectsLiveData
    
    fun filterByCategory(category: PhotoCategory?) {
        _selectedCategory.value = category
    }
    
    fun filterByProject(projectName: String?) {
        _selectedProject.value = projectName
    }
    
    fun addPhoto(photo: SitePhoto) {
        viewModelScope.launch {
            repository.insertPhoto(photo)
        }
    }
    
    fun updatePhoto(photo: SitePhoto) {
        viewModelScope.launch {
            repository.updatePhoto(photo)
        }
    }
    
    fun deletePhoto(photo: SitePhoto) {
        viewModelScope.launch {
            repository.deletePhoto(photo)
        }
    }
    
    fun addProject(project: Project) {
        viewModelScope.launch {
            repository.insertProject(project)
        }
    }
    
    fun searchPhotos(query: String): LiveData<List<SitePhoto>> {
        return repository.searchPhotos(query).asLiveData()
    }
    
    fun getPhotosByProject(projectName: String): LiveData<List<SitePhoto>> {
        return repository.getPhotosByProject(projectName).asLiveData()
    }
    
    suspend fun getPhotoCountByProject(projectName: String): Int {
        return repository.getPhotoCountByProject(projectName)
    }
}

/**
 * ViewModelFactory for PhotosViewModel
 */
class PhotosViewModelFactory(
    private val photoDao: PhotoDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotosViewModel(photoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
