package com.nammamistri.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Entity representing a daily wage entry for a worker
 */
@Parcelize
@Entity(
    tableName = "wage_entries",
    foreignKeys = [
        ForeignKey(
            entity = Worker::class,
            parentColumns = ["id"],
            childColumns = ["workerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["workerId"])]
)
data class WageEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workerId: Long,
    val date: Long,
    val isPresent: Boolean,
    val advancePayment: Double = 0.0,
    val notes: String = ""
) : Parcelable
