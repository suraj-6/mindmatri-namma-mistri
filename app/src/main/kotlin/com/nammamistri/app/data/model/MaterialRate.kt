package com.nammamistri.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing current market rates for construction materials
 */
@Entity(tableName = "material_rates")
data class MaterialRate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val materialName: String,
    val unit: String,
    val pricePerUnit: Double,
    val lastUpdated: Long = System.currentTimeMillis()
)
