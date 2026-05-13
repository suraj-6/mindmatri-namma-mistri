package com.nammamistri.app.utils

/**
 * App-wide constants
 */
object Constants {
    
    // Standard dimensions and ratios
    const val BRICK_SIZE_LENGTH_INCH = 9.0
    const val BRICK_SIZE_WIDTH_INCH = 4.0
    const val BRICK_SIZE_HEIGHT_INCH = 3.0
    
    const val MORTAR_THICKNESS_INCH = 0.5
    
    // Unit conversions
    const val INCH_TO_FEET = 12.0
    const val FEET_TO_METER = 0.3048
    const val SQFT_TO_SQM = 0.0929
    const val CFT_TO_CUM = 0.0283
    
    // Standard mix ratios
    object MixRatio {
        // Cement : Sand ratios
        val PLASTERING_12MM = Pair(1, 6) // 1:6 ratio
        val PLASTERING_20MM = Pair(1, 4) // 1:4 ratio
        val BRICKWORK_9INCH = Pair(1, 6) // 1:6 ratio
        val FLOORING = Pair(1, 4) // 1:4 ratio
        
        // Concrete ratios (Cement : Sand : Aggregate)
        val M10 = Triple(1, 3, 6) // 1:3:6
        val M15 = Triple(1, 2, 4) // 1:2:4
        val M20 = Triple(1, 1.5, 3) // 1:1.5:3
        val M25 = Triple(1, 1, 2) // 1:1:2
    }
    
    // Cement bag weight
    const val CEMENT_BAG_WEIGHT_KG = 50.0
    const val CEMENT_BAG_VOLUME_CFT = 1.226
    
    // Standard coverage
    object Coverage {
        const val PAINT_SQFT_PER_LITER = 100.0 // approx
        const val PRIMER_SQFT_PER_LITER = 120.0
        const val PUTTY_SQFT_PER_KG = 20.0
        const val WATERPROOFING_SQFT_PER_KG = 5.0
    }
    
    // Work hours
    const val STANDARD_WORK_HOURS = 8.0
    const val OVERTIME_MULTIPLIER = 1.5
    
    // File paths
    const val PHOTOS_DIRECTORY = "NammaMistri/Photos"
    const val EXPORTS_DIRECTORY = "NammaMistri/Exports"
    
    // Date formats
    const val DATE_FORMAT_DISPLAY = "dd MMM yyyy"
    const val DATE_FORMAT_FILE = "yyyyMMdd_HHmmss"
    const val DATE_FORMAT_MONTH = "MMMM yyyy"
    
    // Request codes
    const val REQUEST_CAMERA_PERMISSION = 100
    const val REQUEST_STORAGE_PERMISSION = 101
    const val REQUEST_IMAGE_CAPTURE = 102
    const val REQUEST_IMAGE_PICK = 103
}
