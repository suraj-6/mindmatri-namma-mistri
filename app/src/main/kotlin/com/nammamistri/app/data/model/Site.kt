package com.nammamistri.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a construction site
 */
@Entity(tableName = "sites")
data class Site(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val location: String,
    val startDate: Long,
    val isActive: Boolean = true
)
