package com.fairybow.caloriediary

import android.app.Application
import androidx.room.Room
import com.fairybow.caloriediary.data.AppDatabase
import com.fairybow.caloriediary.data.Biometrics
import com.fairybow.caloriediary.data.JournalEntry
import com.fairybow.caloriediary.data.Preferences
import com.fairybow.caloriediary.data.ZeroHourDate
import com.fairybow.caloriediary.debug.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalorieDiary : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDatabase::class.java, "caloriediary-database").build()

        Logger.d("Database created: $database")

        // Insert entities into the database
        CoroutineScope(Dispatchers.IO).launch {
            val biometricsDao = database.biometricsDao()
            val preferencesDao = database.preferencesDao()
            val journalDao = database.journalDao()

            var biometrics = biometricsDao.getBiometrics()

            if (biometrics == null) {
                biometrics = Biometrics()

                val rowId = biometricsDao.insertBiometrics(biometrics)
                Logger.d("Biometrics inserted at row: $rowId")
            }

            var preferences = preferencesDao.getPreferences()

            if (preferences == null) {
                preferences = Preferences()

                val rowId = preferencesDao.insertPreferences(preferences)
                Logger.d("Preferences inserted at row: $rowId")
            }

            // TODO: What if app is open from 11:59 to midnight?
            val today = ZeroHourDate()
            var journalEntry = journalDao.getJournalEntry(today)

            if (journalEntry == null) {
                val previousEntry = journalDao.getPreviousJournalEntry(today)

                journalEntry = JournalEntry(
                    kilograms = previousEntry?.kilograms ?: 0.0,
                    lastCheckInDate = previousEntry?.lastCheckInDate
                )

                val rowId = journalDao.insertJournalEntry(journalEntry)
                Logger.d("JournalEntry: $journalEntry inserted at row: $rowId")
            }

            journalDao.clean()
        }
    }
}
