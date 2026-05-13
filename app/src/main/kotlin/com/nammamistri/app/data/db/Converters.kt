package com.nammamistri.app.data.db

import androidx.room.TypeConverter
import com.nammamistri.app.data.model.*

/**
 * Type converters for Room database
 * Converts enum types to/from strings for storage
 */
class Converters {
    
    // CalculationType converters
    @TypeConverter
    fun fromCalculationType(value: CalculationType): String = value.name
    
    @TypeConverter
    fun toCalculationType(value: String): CalculationType = 
        CalculationType.valueOf(value)
    
    // LaborSkill converters
    @TypeConverter
    fun fromLaborSkill(value: LaborSkill): String = value.name
    
    @TypeConverter
    fun toLaborSkill(value: String): LaborSkill = 
        LaborSkill.valueOf(value)
    
    // AttendanceStatus converters
    @TypeConverter
    fun fromAttendanceStatus(value: AttendanceStatus): String = value.name
    
    @TypeConverter
    fun toAttendanceStatus(value: String): AttendanceStatus = 
        AttendanceStatus.valueOf(value)
    
    // PaymentMode converters
    @TypeConverter
    fun fromPaymentMode(value: PaymentMode): String = value.name
    
    @TypeConverter
    fun toPaymentMode(value: String): PaymentMode = 
        PaymentMode.valueOf(value)
    
    // PhotoCategory converters
    @TypeConverter
    fun fromPhotoCategory(value: PhotoCategory): String = value.name
    
    @TypeConverter
    fun toPhotoCategory(value: String): PhotoCategory = 
        PhotoCategory.valueOf(value)
    
    // ProjectStatus converters
    @TypeConverter
    fun fromProjectStatus(value: ProjectStatus): String = value.name
    
    @TypeConverter
    fun toProjectStatus(value: String): ProjectStatus = 
        ProjectStatus.valueOf(value)
    
    // RateCategory converters
    @TypeConverter
    fun fromRateCategory(value: RateCategory): String = value.name
    
    @TypeConverter
    fun toRateCategory(value: String): RateCategory = 
        RateCategory.valueOf(value)
}
