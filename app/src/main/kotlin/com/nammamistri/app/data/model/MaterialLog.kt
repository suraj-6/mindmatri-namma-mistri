package com.nammamistri.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Wall thickness options
 */
enum class WallThickness(val displayName: String, val inches: Double) {
    FOUR_AND_HALF("4.5 inch", 4.5),
    NINE("9 inch", 9.0),
    THIRTEEN_AND_HALF("13.5 inch", 13.5)
}

/**
 * Entity representing a material calculation log for a site
 */
@Entity(
    tableName = "material_logs",
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
data class MaterialLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val siteId: Long,
    val bricks: Int,
    val cementBags: Int,
    val sandLoads: Double,
    val wallLength: Double,
    val wallWidth: Double,
    val wallHeight: Double,
    val wallThickness: String, // "4.5 inch", "9 inch", "13.5 inch"
    val calculatedOn: Long = System.currentTimeMillis()
)
