package com.nammamistri.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Entity representing a worker at a construction site
 */
@Parcelize
@Entity(
    tableName = "workers",
    foreignKeys = [
        ForeignKey(
            entity = Site::class,
            parentColumns = ["id"],
            childColumns = ["siteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["siteId"])]
)
data class Worker(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val siteId: Long,
    val name: String,
    val dailyWage: Double,
    val phoneNumber: String,
    val joiningDate: Long = System.currentTimeMillis()
) : Parcelable
