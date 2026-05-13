package com.nammamistri.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Entity representing a material calculation record
 */
@Parcelize
@Entity(tableName = "calculations")
data class Calculation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val projectName: String,
    val calculationType: CalculationType,
    val length: Double,
    val width: Double,
    val height: Double = 0.0,
    val area: Double,
    val quantity: Double,
    val unit: String,
    val materialCost: Double = 0.0,
    val laborCost: Double = 0.0,
    val totalCost: Double = 0.0,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

/**
 * Types of calculations supported
 */
enum class CalculationType {
    BRICK_WORK,
    PLASTERING,
    FLOORING,
    PAINTING,
    CONCRETE,
    STEEL,
    CEMENT_SAND,
    TILE_WORK,
    WATERPROOFING,
    CUSTOM
}

/**
 * Material item for calculation breakdown
 */
@Parcelize
@Entity(tableName = "materials")
data class Material(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val calculationId: Long,
    val name: String,
    val quantity: Double,
    val unit: String,
    val rate: Double,
    val amount: Double
) : Parcelable
