package com.nammamistri.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nammamistri.app.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room Database for NammaMistri app
 */
@Database(
    entities = [
        Calculation::class,
        Material::class,
        Labor::class,
        Attendance::class,
        Payment::class,
        SitePhoto::class,
        Project::class,
        StandardRate::class,
        RateHistory::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class NammaMistriDatabase : RoomDatabase() {
    
    abstract fun calculationDao(): CalculationDao
    abstract fun laborDao(): LaborDao
    abstract fun photoDao(): PhotoDao
    abstract fun ratesDao(): RatesDao
    
    companion object {
        @Volatile
        private var INSTANCE: NammaMistriDatabase? = null
        
        fun getDatabase(context: Context): NammaMistriDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NammaMistriDatabase::class.java,
                    "nammamistri_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    
    /**
     * Callback to populate database with default rates on first creation
     */
    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDefaultRates(database.ratesDao())
                }
            }
        }
        
        private suspend fun populateDefaultRates(ratesDao: RatesDao) {
            val defaultRates = listOf(
                // Cement
                StandardRate(
                    name = "OPC 53 Grade Cement",
                    category = RateCategory.CEMENT,
                    rate = 380.0,
                    unit = RateUnits.BAG,
                    description = "Ordinary Portland Cement 53 Grade (50kg bag)",
                    brand = "UltraTech/ACC/Ambuja",
                    isDefault = true
                ),
                StandardRate(
                    name = "PPC Cement",
                    category = RateCategory.CEMENT,
                    rate = 350.0,
                    unit = RateUnits.BAG,
                    description = "Portland Pozzolana Cement (50kg bag)",
                    brand = "UltraTech/ACC/Ambuja",
                    isDefault = true
                ),
                
                // Sand
                StandardRate(
                    name = "M-Sand (Manufactured Sand)",
                    category = RateCategory.SAND,
                    rate = 55.0,
                    unit = RateUnits.CFT,
                    description = "Manufactured sand for construction",
                    isDefault = true
                ),
                StandardRate(
                    name = "River Sand",
                    category = RateCategory.SAND,
                    rate = 75.0,
                    unit = RateUnits.CFT,
                    description = "Natural river sand",
                    isDefault = true
                ),
                StandardRate(
                    name = "P-Sand (Plastering Sand)",
                    category = RateCategory.SAND,
                    rate = 45.0,
                    unit = RateUnits.CFT,
                    description = "Fine sand for plastering",
                    isDefault = true
                ),
                
                // Aggregate
                StandardRate(
                    name = "20mm Aggregate",
                    category = RateCategory.AGGREGATE,
                    rate = 42.0,
                    unit = RateUnits.CFT,
                    description = "20mm crushed stone aggregate",
                    isDefault = true
                ),
                StandardRate(
                    name = "40mm Aggregate",
                    category = RateCategory.AGGREGATE,
                    rate = 38.0,
                    unit = RateUnits.CFT,
                    description = "40mm crushed stone aggregate",
                    isDefault = true
                ),
                
                // Bricks
                StandardRate(
                    name = "Red Clay Bricks",
                    category = RateCategory.BRICK,
                    rate = 8500.0,
                    unit = RateUnits.THOUSAND,
                    description = "Standard red clay bricks (9\"x4\"x3\")",
                    isDefault = true
                ),
                StandardRate(
                    name = "AAC Blocks (4 inch)",
                    category = RateCategory.BRICK,
                    rate = 45.0,
                    unit = RateUnits.PIECE,
                    description = "Autoclaved Aerated Concrete blocks",
                    isDefault = true
                ),
                StandardRate(
                    name = "Solid Concrete Blocks (6 inch)",
                    category = RateCategory.BRICK,
                    rate = 38.0,
                    unit = RateUnits.PIECE,
                    description = "Solid concrete blocks",
                    isDefault = true
                ),
                
                // Steel
                StandardRate(
                    name = "TMT Steel Bar (Fe500)",
                    category = RateCategory.STEEL,
                    rate = 65.0,
                    unit = RateUnits.KG,
                    description = "TMT reinforcement bars Fe500 grade",
                    brand = "TATA/JSW/SAIL",
                    isDefault = true
                ),
                StandardRate(
                    name = "Binding Wire",
                    category = RateCategory.STEEL,
                    rate = 85.0,
                    unit = RateUnits.KG,
                    description = "Steel binding wire for reinforcement",
                    isDefault = true
                ),
                
                // Labor Rates
                StandardRate(
                    name = "Mason (Skilled)",
                    category = RateCategory.LABOR_MASON,
                    rate = 900.0,
                    unit = RateUnits.DAY,
                    description = "Daily wage for skilled mason",
                    isDefault = true
                ),
                StandardRate(
                    name = "Helper/Coolie",
                    category = RateCategory.LABOR_HELPER,
                    rate = 550.0,
                    unit = RateUnits.DAY,
                    description = "Daily wage for unskilled helper",
                    isDefault = true
                ),
                StandardRate(
                    name = "Carpenter",
                    category = RateCategory.LABOR_CARPENTER,
                    rate = 850.0,
                    unit = RateUnits.DAY,
                    description = "Daily wage for carpenter",
                    isDefault = true
                ),
                StandardRate(
                    name = "Plumber",
                    category = RateCategory.LABOR_PLUMBER,
                    rate = 800.0,
                    unit = RateUnits.DAY,
                    description = "Daily wage for plumber",
                    isDefault = true
                ),
                StandardRate(
                    name = "Electrician",
                    category = RateCategory.LABOR_ELECTRICIAN,
                    rate = 750.0,
                    unit = RateUnits.DAY,
                    description = "Daily wage for electrician",
                    isDefault = true
                ),
                StandardRate(
                    name = "Painter",
                    category = RateCategory.LABOR_PAINTER,
                    rate = 700.0,
                    unit = RateUnits.DAY,
                    description = "Daily wage for painter",
                    isDefault = true
                ),
                
                // Work Rates
                StandardRate(
                    name = "Brickwork (9\" wall)",
                    category = RateCategory.WORK_BRICKWORK,
                    rate = 45.0,
                    unit = RateUnits.SQFT,
                    description = "Brickwork including materials and labor",
                    isDefault = true
                ),
                StandardRate(
                    name = "Plastering (12mm)",
                    category = RateCategory.WORK_PLASTERING,
                    rate = 25.0,
                    unit = RateUnits.SQFT,
                    description = "Cement plastering 12mm thick",
                    isDefault = true
                ),
                StandardRate(
                    name = "Flooring (Vitrified Tiles)",
                    category = RateCategory.WORK_FLOORING,
                    rate = 85.0,
                    unit = RateUnits.SQFT,
                    description = "Vitrified tile flooring with material",
                    isDefault = true
                ),
                StandardRate(
                    name = "Wall Painting (2 coats)",
                    category = RateCategory.WORK_PAINTING,
                    rate = 18.0,
                    unit = RateUnits.SQFT,
                    description = "Interior emulsion painting 2 coats",
                    isDefault = true
                )
            )
            
            ratesDao.insertRates(defaultRates)
        }
    }
}
