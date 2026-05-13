package com.nammamistri.app.utils

import kotlin.math.ceil

/**
 * Civil Engineering Construction Formulas
 * All calculations are based on standard Indian construction practices
 */
object ConstructionFormulas {
    
    // Constants
    private const val METER_TO_FEET = 3.28084
    private const val CUBIC_METER_TO_CFT = 35.314
    private const val BRICK_VOLUME_CFT = 0.0573  // Standard brick 9x4.5x3 inch with mortar
    private const val MORTAR_RATIO = 0.30  // 30% mortar ratio for brickwork
    private const val DRY_MIX_FACTOR = 1.33  // Wet to dry volume conversion
    private const val CEMENT_TO_MORTAR_RATIO = 0.22  // 1:6 ratio (1/7 ≈ 0.14, with wastage 0.22)
    private const val CFT_PER_CEMENT_BAG = 1.226  // Volume of 50kg cement bag
    private const val CFT_PER_SAND_LOAD = 100.0  // 1 load = 100 cft
    
    /**
     * Calculate number of bricks required for a wall
     * 
     * @param lengthM Wall length in meters
     * @param heightM Wall height in meters
     * @param thicknessInch Wall thickness in inches (4.5, 9, or 13.5)
     * @return Number of bricks required (rounded up, includes wastage)
     */
    fun calculateBricks(lengthM: Double, heightM: Double, thicknessInch: Double): Int {
        // Convert meters to feet
        val lengthFt = lengthM * METER_TO_FEET
        val heightFt = heightM * METER_TO_FEET
        val thicknessFt = thicknessInch / 12.0
        
        // Wall volume in cubic feet
        val wallVolumeCFT = lengthFt * heightFt * thicknessFt
        
        // Number of bricks = Wall volume / Brick volume
        val bricks = wallVolumeCFT / BRICK_VOLUME_CFT
        
        // Add 5% wastage
        val bricksWithWastage = bricks * 1.05
        
        return ceil(bricksWithWastage).toInt()
    }
    
    /**
     * Calculate cement bags required for brickwork mortar
     * 
     * @param lengthM Wall length in meters
     * @param heightM Wall height in meters
     * @param thicknessInch Wall thickness in inches
     * @return Number of cement bags (50kg each)
     */
    fun calculateCement(lengthM: Double, heightM: Double, thicknessInch: Double): Int {
        // Wall volume in cubic meters
        val wallVolumeCUM = lengthM * heightM * (thicknessInch / 39.37)
        
        // Mortar volume = 30% of wall volume (for joints)
        val mortarVolumeCUM = wallVolumeCUM * MORTAR_RATIO
        
        // Convert to CFT for calculation
        val mortarVolumeCFT = mortarVolumeCUM * CUBIC_METER_TO_CFT
        
        // Dry volume (add 33% for compaction)
        val dryVolumeCFT = mortarVolumeCFT * DRY_MIX_FACTOR
        
        // In 1:6 ratio, cement = 1/7 of total
        val cementVolumeCFT = dryVolumeCFT / 7.0
        
        // Cement bags (1 bag = 1.226 cft)
        val cementBags = cementVolumeCFT / CFT_PER_CEMENT_BAG
        
        // Add 10% wastage
        val bagsWithWastage = cementBags * 1.10
        
        return ceil(bagsWithWastage).toInt()
    }
    
    /**
     * Calculate sand loads required for brickwork mortar
     * 
     * @param cementBags Number of cement bags
     * @return Number of sand loads (1 load = 100 cft)
     */
    fun calculateSand(cementBags: Int): Double {
        // In 1:6 ratio, sand = 6 parts for every 1 part cement
        // 1 cement bag = 1.226 cft
        val sandVolumeCFT = cementBags * 1.226 * 6.0
        
        // Convert to loads (1 load = 100 cft)
        return sandVolumeCFT / CFT_PER_SAND_LOAD
    }
    
    /**
     * Alternative sand calculation using direct volume
     * 
     * @param lengthM Wall length in meters
     * @param heightM Wall height in meters
     * @param thicknessInch Wall thickness in inches
     * @return Sand in CFT
     */
    fun calculateSandCFT(lengthM: Double, heightM: Double, thicknessInch: Double): Double {
        // Wall volume in cubic meters
        val wallVolumeCUM = lengthM * heightM * (thicknessInch / 39.37)
        
        // Mortar volume
        val mortarVolumeCFT = wallVolumeCUM * MORTAR_RATIO * CUBIC_METER_TO_CFT
        
        // Dry volume
        val dryVolumeCFT = mortarVolumeCFT * DRY_MIX_FACTOR
        
        // Sand = 6/7 of dry volume (1:6 ratio)
        return dryVolumeCFT * 6.0 / 7.0
    }
    
    /**
     * Complete brickwork calculation
     * 
     * @param lengthM Wall length in meters
     * @param heightM Wall height in meters
     * @param thicknessInch Wall thickness in inches
     * @return BrickworkResult with all calculated values
     */
    fun calculateBrickwork(
        lengthM: Double,
        heightM: Double,
        thicknessInch: Double
    ): BrickworkResult {
        val bricks = calculateBricks(lengthM, heightM, thicknessInch)
        val cementBags = calculateCement(lengthM, heightM, thicknessInch)
        val sandLoads = calculateSand(cementBags)
        val sandCFT = calculateSandCFT(lengthM, heightM, thicknessInch)
        
        // Wall area in square meters
        val wallAreaSqM = lengthM * heightM
        
        // Wall area in square feet
        val wallAreaSqFt = wallAreaSqM * 10.764
        
        return BrickworkResult(
            bricks = bricks,
            cementBags = cementBags,
            sandLoads = sandLoads,
            sandCFT = sandCFT,
            wallAreaSqM = wallAreaSqM,
            wallAreaSqFt = wallAreaSqFt
        )
    }
    
    /**
     * Calculate estimated cost for materials
     * 
     * @param bricks Number of bricks
     * @param cementBags Number of cement bags
     * @param sandLoads Number of sand loads
     * @param brickRatePerThousand Rate per 1000 bricks
     * @param cementRatePerBag Rate per cement bag
     * @param sandRatePerCFT Rate per CFT of sand
     * @return Total estimated cost
     */
    fun calculateEstimatedCost(
        bricks: Int,
        cementBags: Int,
        sandLoads: Double,
        brickRatePerThousand: Double = 8500.0,
        cementRatePerBag: Double = 380.0,
        sandRatePerCFT: Double = 55.0
    ): CostEstimate {
        val brickCost = (bricks / 1000.0) * brickRatePerThousand
        val cementCost = cementBags * cementRatePerBag
        val sandCost = sandLoads * CFT_PER_SAND_LOAD * sandRatePerCFT
        val totalCost = brickCost + cementCost + sandCost
        
        return CostEstimate(
            brickCost = brickCost,
            cementCost = cementCost,
            sandCost = sandCost,
            totalCost = totalCost
        )
    }
    
    /**
     * Calculate plastering materials
     * 
     * @param areaSqM Area to plaster in square meters
     * @param thicknessMM Plaster thickness in mm (12 or 20)
     * @param ratio Cement:Sand ratio (e.g., 6 for 1:6)
     * @return PlasteringResult
     */
    fun calculatePlastering(areaSqM: Double, thicknessMM: Int = 12, ratio: Int = 6): PlasteringResult {
        // Convert thickness to meters
        val thicknessM = thicknessMM / 1000.0
        
        // Wet volume in cubic meters
        val wetVolumeCUM = areaSqM * thicknessM
        
        // Dry volume (add 35%)
        val dryVolumeCUM = wetVolumeCUM * 1.35
        
        // Total parts = 1 + ratio
        val totalParts = 1 + ratio
        
        // Cement volume in CUM
        val cementVolumeCUM = dryVolumeCUM / totalParts
        
        // Cement bags (1 bag = 0.0347 CUM)
        val cementBags = ceil(cementVolumeCUM / 0.0347).toInt()
        
        // Sand in CFT
        val sandCFT = (dryVolumeCUM * ratio / totalParts) * CUBIC_METER_TO_CFT
        
        return PlasteringResult(
            areaSqM = areaSqM,
            cementBags = cementBags,
            sandCFT = sandCFT
        )
    }
}

/**
 * Result of brickwork calculation
 */
data class BrickworkResult(
    val bricks: Int,
    val cementBags: Int,
    val sandLoads: Double,
    val sandCFT: Double,
    val wallAreaSqM: Double,
    val wallAreaSqFt: Double
)

/**
 * Cost estimate breakdown
 */
data class CostEstimate(
    val brickCost: Double,
    val cementCost: Double,
    val sandCost: Double,
    val totalCost: Double
)

/**
 * Plastering calculation result
 */
data class PlasteringResult(
    val areaSqM: Double,
    val cementBags: Int,
    val sandCFT: Double
)
