package com.nammamistri.app

import android.app.Application
import com.nammamistri.app.data.db.AppDatabase

/**
 * Application class for NammaMistri
 * Initializes database and other app-wide components
 */
class NammaMistriApp : Application() {
    
    // Lazy initialization of database
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    
    companion object {
        lateinit var instance: NammaMistriApp
            private set
        
        // Convenience accessor for database
        val db: AppDatabase
            get() = instance.database
    }
}
