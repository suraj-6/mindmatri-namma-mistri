package com.nammamistri.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nammamistri.app.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room Database for NammaMistri app
 * Contains all 5 entities: Site, MaterialLog, Worker, WageEntry, MaterialRate
 */
@Database(
    entities = [
        Site::class,
        MaterialLog::class,
        Worker::class,
        WageEntry::class,
        MaterialRate::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun siteDao(): SiteDao
    abstract fun materialLogDao(): MaterialLogDao
    abstract fun workerDao(): WorkerDao
    abstract fun wageEntryDao(): WageEntryDao
    abstract fun materialRateDao(): MaterialRateDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nammamistri_app_database"
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
     * Callback to populate database with default material rates on first creation
     */
    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDefaultRates(database.materialRateDao())
                }
            }
        }
        
        private suspend fun populateDefaultRates(materialRateDao: MaterialRateDao) {
            val defaultRates = listOf(
                // Construction Materials
                MaterialRate(
                    materialName = "Brick",
                    unit = "per 1000 nos",
                    pricePerUnit = 7000.0
                ),
                MaterialRate(
                    materialName = "Cement (OPC 53)",
                    unit = "per bag (50kg)",
                    pricePerUnit = 380.0
                ),
                MaterialRate(
                    materialName = "River Sand",
                    unit = "per load (100 cft)",
                    pricePerUnit = 4500.0
                ),
                MaterialRate(
                    materialName = "M-Sand",
                    unit = "per load (100 cft)",
                    pricePerUnit = 3200.0
                ),
                MaterialRate(
                    materialName = "20mm Aggregate",
                    unit = "per load",
                    pricePerUnit = 3800.0
                ),
                MaterialRate(
                    materialName = "Steel (TMT 8mm)",
                    unit = "per kg",
                    pricePerUnit = 65.0
                ),
                
                // Labor Rates
                MaterialRate(
                    materialName = "Labour (Mason)",
                    unit = "per day",
                    pricePerUnit = 800.0
                ),
                MaterialRate(
                    materialName = "Labour (Helper)",
                    unit = "per day",
                    pricePerUnit = 500.0
                ),
                
                // Additional common materials
                MaterialRate(
                    materialName = "Red Bricks",
                    unit = "per 1000",
                    pricePerUnit = 8500.0
                ),
                MaterialRate(
                    materialName = "Cement (OPC 53)",
                    unit = "per bag (50kg)",
                    pricePerUnit = 380.0
                ),
                MaterialRate(
                    materialName = "Cement (PPC)",
                    unit = "per bag (50kg)",
                    pricePerUnit = 350.0
                ),
                MaterialRate(
                    materialName = "M-Sand",
                    unit = "per cft",
                    pricePerUnit = 55.0
                ),
                MaterialRate(
                    materialName = "River Sand",
                    unit = "per cft",
                    pricePerUnit = 75.0
                ),
                MaterialRate(
                    materialName = "P-Sand (Plastering)",
                    unit = "per cft",
                    pricePerUnit = 45.0
                ),
                MaterialRate(
                    materialName = "20mm Aggregate",
                    unit = "per cft",
                    pricePerUnit = 42.0
                ),
                MaterialRate(
                    materialName = "40mm Aggregate",
                    unit = "per cft",
                    pricePerUnit = 38.0
                ),
                MaterialRate(
                    materialName = "TMT Steel (Fe500)",
                    unit = "per kg",
                    pricePerUnit = 65.0
                ),
                MaterialRate(
                    materialName = "Binding Wire",
                    unit = "per kg",
                    pricePerUnit = 85.0
                ),
                MaterialRate(
                    materialName = "AAC Blocks (4 inch)",
                    unit = "per piece",
                    pricePerUnit = 45.0
                ),
                MaterialRate(
                    materialName = "AAC Blocks (6 inch)",
                    unit = "per piece",
                    pricePerUnit = 55.0
                ),
                MaterialRate(
                    materialName = "Solid Concrete Block",
                    unit = "per piece",
                    pricePerUnit = 38.0
                ),
                MaterialRate(
                    materialName = "Water Proofer",
                    unit = "per kg",
                    pricePerUnit = 45.0
                ),
                MaterialRate(
                    materialName = "Mason Labor",
                    unit = "per day",
                    pricePerUnit = 900.0
                ),
                MaterialRate(
                    materialName = "Helper Labor",
                    unit = "per day",
                    pricePerUnit = 550.0
                )
            )
            
            materialRateDao.insertAll(defaultRates)
        }
    }
}
