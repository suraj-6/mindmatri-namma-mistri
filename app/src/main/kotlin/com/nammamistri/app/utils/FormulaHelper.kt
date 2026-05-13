package com.nammamistri.app.utils

import com.nammamistri.app.utils.Constants.CEMENT_BAG_VOLUME_CFT
import com.nammamistri.app.utils.Constants.CFT_TO_CUM
import kotlin.math.ceil

/**
 * Construction calculation formulas
 */
object FormulaHelper {
    
    /**
     * Calculate area in square feet
     */
    fun calculateArea(length: Double, width: Double): Double {
        return length * width
    }
    
    /**
     * Calculate volume in cubic feet
     */
    fun calculateVolume(length: Double, width: Double, height: Double): Double {
        return length * width * height
    }
    
    /**
     * Convert square feet to square meters
     */
    fun sqftToSqm(sqft: Double): Double {
        return sqft * Constants.SQFT_TO_SQM
    }
    
    /**
     * Convert cubic feet to cubic meters
     */
    fun cftToCum(cft: Double): Double {
        return cft * CFT_TO_CUM
    }
    
    // ============== BRICK CALCULATIONS ==============
    
    /**
     * Calculate number of bricks required for a wall
     * @param length Wall length in feet
     * @param height Wall height in feet
     * @param thickness Wall thickness in inches (4.5 or 9)
     * @param withMortar Include mortar joints
     * @return Number of bricks required
     */
    fun calculateBricks(
        length: Double,
        height: Double,
        thickness: Double = 9.0,
        withMortar: Boolean = true
    ): BrickResult {
        val areaSqft = length * height
        
        // Standard brick size with mortar
        val brickLength = if (withMortar) 9.5 else 9.0 // inches
        val brickHeight = if (withMortar) 3.5 else 3.0 // inches
        val brickWidth = if (withMortar) 4.5 else 4.0 // inches
        
        // Convert to feet
        val brickLengthFt = brickLength / 12
        val brickHeightFt = brickHeight / 12
        val brickWidthFt = brickWidth / 12
        
        // Volume of one brick in cft
        val brickVolume = brickLengthFt * brickHeightFt * brickWidthFt
        
        // Wall volume in cft
        val wallVolume = length * height * (thickness / 12)
        
        // Number of bricks
        val numberOfBricks = ceil(wallVolume / brickVolume).toInt()
        
        // Add 5% wastage
        val bricksWithWastage = ceil(numberOfBricks * 1.05).toInt()
        
        // Mortar required (approx 30% of brick volume for 9" wall)
        val mortarVolume = wallVolume * 0.30
        
        return BrickResult(
            numberOfBricks = bricksWithWastage,
            areaSqft = areaSqft,
            mortarVolumeCft = mortarVolume
        )
    }
    
    data class BrickResult(
        val numberOfBricks: Int,
        val areaSqft: Double,
        val mortarVolumeCft: Double
    )
    
    // ============== PLASTERING CALCULATIONS ==============
    
    /**
     * Calculate plastering materials
     * @param areaSqft Area to be plastered in sqft
     * @param thickness Plaster thickness in mm (12 or 20)
     * @param ratio Cement:Sand ratio (1:4 or 1:6)
     * @return Plastering materials required
     */
    fun calculatePlastering(
        areaSqft: Double,
        thickness: Int = 12,
        ratio: Pair<Int, Int> = Pair(1, 6)
    ): PlasteringResult {
        // Convert thickness to feet
        val thicknessFt = thickness / 304.8 // mm to feet
        
        // Wet volume in cft
        val wetVolume = areaSqft * thicknessFt
        
        // Dry volume (add 35% for shrinkage)
        val dryVolume = wetVolume * 1.35
        
        // Total parts in ratio
        val totalParts = ratio.first + ratio.second
        
        // Cement volume in cft
        val cementVolume = dryVolume * ratio.first / totalParts
        
        // Cement in bags (1 bag = 1.226 cft)
        val cementBags = ceil(cementVolume / CEMENT_BAG_VOLUME_CFT)
        
        // Sand volume in cft
        val sandVolume = dryVolume * ratio.second / totalParts
        
        return PlasteringResult(
            areaSqft = areaSqft,
            cementBags = cementBags,
            sandCft = sandVolume
        )
    }
    
    data class PlasteringResult(
        val areaSqft: Double,
        val cementBags: Double,
        val sandCft: Double
    )
    
    // ============== CONCRETE CALCULATIONS ==============
    
    /**
     * Calculate concrete materials (RCC)
     * @param volumeCft Volume of concrete required in cft
     * @param grade Concrete grade (M15, M20, M25)
     * @return Concrete materials required
     */
    fun calculateConcrete(
        volumeCft: Double,
        grade: String = "M20"
    ): ConcreteResult {
        // Dry volume factor (54% increase)
        val dryVolume = volumeCft * 1.54
        
        // Get ratio based on grade
        val ratio = when (grade.uppercase()) {
            "M10" -> Triple(1.0, 3.0, 6.0)
            "M15" -> Triple(1.0, 2.0, 4.0)
            "M20" -> Triple(1.0, 1.5, 3.0)
            "M25" -> Triple(1.0, 1.0, 2.0)
            else -> Triple(1.0, 1.5, 3.0) // Default M20
        }
        
        val totalParts = ratio.first + ratio.second + ratio.third
        
        // Cement in cft, then convert to bags
        val cementVolume = dryVolume * ratio.first / totalParts
        val cementBags = ceil(cementVolume / CEMENT_BAG_VOLUME_CFT)
        
        // Sand in cft
        val sandVolume = dryVolume * ratio.second / totalParts
        
        // Aggregate in cft
        val aggregateVolume = dryVolume * ratio.third / totalParts
        
        return ConcreteResult(
            volumeCft = volumeCft,
            cementBags = cementBags,
            sandCft = sandVolume,
            aggregateCft = aggregateVolume
        )
    }
    
    data class ConcreteResult(
        val volumeCft: Double,
        val cementBags: Double,
        val sandCft: Double,
        val aggregateCft: Double
    )
    
    // ============== STEEL CALCULATIONS ==============
    
    /**
     * Calculate steel reinforcement weight
     * @param lengthMeters Total length of bars in meters
     * @param diameter Bar diameter in mm
     * @return Weight in kg
     */
    fun calculateSteelWeight(lengthMeters: Double, diameter: Int): Double {
        // Formula: (D^2 / 162) * Length
        return (diameter * diameter / 162.0) * lengthMeters
    }
    
    /**
     * Calculate number of bars from total length
     * @param totalLengthMeters Total length needed
     * @param barLengthMeters Standard bar length (usually 12m)
     * @return Number of bars
     */
    fun calculateNumberOfBars(totalLengthMeters: Double, barLengthMeters: Double = 12.0): Int {
        return ceil(totalLengthMeters / barLengthMeters).toInt()
    }
    
    // ============== PAINT CALCULATIONS ==============
    
    /**
     * Calculate paint required
     * @param areaSqft Area to be painted
     * @param coats Number of coats
     * @param coveragePerLiter Coverage per liter in sqft
     * @return Paint required in liters
     */
    fun calculatePaint(
        areaSqft: Double,
        coats: Int = 2,
        coveragePerLiter: Double = Constants.Coverage.PAINT_SQFT_PER_LITER
    ): Double {
        val totalArea = areaSqft * coats
        return ceil(totalArea / coveragePerLiter)
    }
    
    // ============== TILE CALCULATIONS ==============
    
    /**
     * Calculate tiles required
     * @param floorAreaSqft Floor area in sqft
     * @param tileLengthInch Tile length in inches
     * @param tileWidthInch Tile width in inches
     * @param wastagePercent Wastage percentage (default 10%)
     * @return Number of tiles and boxes
     */
    fun calculateTiles(
        floorAreaSqft: Double,
        tileLengthInch: Double,
        tileWidthInch: Double,
        wastagePercent: Double = 10.0,
        tilesPerBox: Int = 4
    ): TileResult {
        // Tile area in sqft
        val tileAreaSqft = (tileLengthInch * tileWidthInch) / 144.0
        
        // Number of tiles (without wastage)
        val tilesNeeded = floorAreaSqft / tileAreaSqft
        
        // Add wastage
        val tilesWithWastage = ceil(tilesNeeded * (1 + wastagePercent / 100)).toInt()
        
        // Number of boxes
        val boxes = ceil(tilesWithWastage.toDouble() / tilesPerBox).toInt()
        
        return TileResult(
            numberOfTiles = tilesWithWastage,
            numberOfBoxes = boxes,
            areaSqft = floorAreaSqft
        )
    }
    
    data class TileResult(
        val numberOfTiles: Int,
        val numberOfBoxes: Int,
        val areaSqft: Double
    )
}
