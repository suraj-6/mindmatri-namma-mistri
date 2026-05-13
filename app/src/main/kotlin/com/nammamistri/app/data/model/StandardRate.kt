package com.nammamistri.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Entity representing standard rates for materials and labor
 */
@Parcelize
@Entity(tableName = "standard_rates")
data class StandardRate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: RateCategory,
    val rate: Double,
    val unit: String,
    val description: String = "",
    val brand: String = "",
    val isDefault: Boolean = false,
    val location: String = "Bangalore", // Default location
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

/**
 * Rate categories
 */
enum class RateCategory(val displayName: String) {
    // Materials
    CEMENT("Cement"),
    SAND("Sand"),
    AGGREGATE("Aggregate / Jelly"),
    BRICK("Bricks"),
    STEEL("Steel / TMT"),
    TILES("Tiles"),
    PAINT("Paint"),
    WOOD("Wood / Timber"),
    ELECTRICAL("Electrical"),
    PLUMBING("Plumbing"),
    HARDWARE("Hardware"),
    
    // Labor Rates
    LABOR_MASON("Mason Rate"),
    LABOR_HELPER("Helper Rate"),
    LABOR_CARPENTER("Carpenter Rate"),
    LABOR_PLUMBER("Plumber Rate"),
    LABOR_ELECTRICIAN("Electrician Rate"),
    LABOR_PAINTER("Painter Rate"),
    
    // Work Rates (per sqft/sqm)
    WORK_BRICKWORK("Brickwork Rate"),
    WORK_PLASTERING("Plastering Rate"),
    WORK_FLOORING("Flooring Rate"),
    WORK_PAINTING("Painting Rate"),
    WORK_TILING("Tiling Rate"),
    
    OTHER("Other")
}

/**
 * Unit types used in rates
 */
object RateUnits {
    const val BAG = "Bag"
    const val CFT = "Cft"
    const val BRASS = "Brass"
    const val PIECE = "Piece"
    const val THOUSAND = "1000 Nos"
    const val KG = "Kg"
    const val QUINTAL = "Quintal"
    const val TON = "Ton"
    const val SQFT = "Sq.ft"
    const val SQM = "Sq.m"
    const val RFT = "Rft"
    const val RM = "R.m"
    const val LITER = "Liter"
    const val DAY = "Per Day"
    const val HOUR = "Per Hour"
    const val LUMPSUM = "Lumpsum"
}

/**
 * Rate history for tracking price changes
 */
@Parcelize
@Entity(tableName = "rate_history")
data class RateHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val rateId: Long,
    val oldRate: Double,
    val newRate: Double,
    val changedAt: Long = System.currentTimeMillis(),
    val reason: String = ""
) : Parcelable
