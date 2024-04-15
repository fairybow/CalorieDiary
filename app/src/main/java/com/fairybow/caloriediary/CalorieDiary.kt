package com.fairybow.caloriediary

import android.app.Application
import androidx.room.Room
import com.fairybow.caloriediary.data.AppDatabase
import com.fairybow.caloriediary.data.Repository
import com.fairybow.caloriediary.debug.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalorieDiary : Application() {
    companion object {
        private lateinit var database: AppDatabase
        lateinit var repository: Repository
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "caloriediary-database"
        ).build()
        Logger.d("Database created: $database")

        repository = Repository(database)

        CoroutineScope(Dispatchers.IO).launch {
            repository.initializeTables()
        }
    }
}
