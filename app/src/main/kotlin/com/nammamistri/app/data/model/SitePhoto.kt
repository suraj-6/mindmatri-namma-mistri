package com.nammamistri.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Entity representing a site photo
 */
@Parcelize
@Entity(tableName = "site_photos")
data class SitePhoto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val projectName: String,
    val photoUri: String,
    val thumbnailUri: String? = null,
    val description: String = "",
    val category: PhotoCategory = PhotoCategory.GENERAL,
    val location: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val takenAt: Long = System.currentTimeMillis(),
    val uploadedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false,
    val tags: String = "" // Comma-separated tags
) : Parcelable

/**
 * Photo categories for organization
 */
enum class PhotoCategory(val displayName: String) {
    GENERAL("General"),
    BEFORE("Before Work"),
    AFTER("After Work"),
    PROGRESS("Work in Progress"),
    FOUNDATION("Foundation"),
    STRUCTURE("Structure"),
    ELECTRICAL("Electrical"),
    PLUMBING("Plumbing"),
    FINISHING("Finishing"),
    ISSUE("Issue/Problem"),
    MATERIAL("Materials"),
    MEASUREMENT("Measurements")
}

/**
 * Project for grouping photos
 */
@Parcelize
@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val clientName: String = "",
    val clientPhone: String = "",
    val address: String = "",
    val startDate: Long? = null,
    val endDate: Long? = null,
    val status: ProjectStatus = ProjectStatus.ONGOING,
    val budget: Double = 0.0,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class ProjectStatus {
    PLANNING,
    ONGOING,
    PAUSED,
    COMPLETED,
    CANCELLED
}
