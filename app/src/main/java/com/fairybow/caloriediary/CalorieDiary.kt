package com.fairybow.caloriediary

import android.app.Application
import androidx.room.Room
import com.fairybow.caloriediary.data.AppDatabase
import com.fairybow.caloriediary.data.biometrics.BiometricsTable
import com.fairybow.caloriediary.data.day.DayTable
import com.fairybow.caloriediary.data.preferences.PreferencesTable
import com.fairybow.caloriediary.debug.Logger
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.ImperialWeight
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate
import com.fairybow.caloriediary.utilities.imperialWeightType
import com.fairybow.caloriediary.utilities.today
import com.fairybow.caloriediary.utilities.usesImperialHeight
import com.fairybow.caloriediary.utilities.usesImperialWeight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class CalorieDiary : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "caloriediary-database"
        ).build()

        Logger.d("Database created: $database")

        CoroutineScope(Dispatchers.IO).launch {
            initBiometrics()
            initDay()
            initPreferences()

            cleanDayTable()
        }
    }

    // TODO: Repository for the below functions
    private suspend fun initBiometrics() {
        val dao = database.biometricsDao()
        var biometrics = dao.get()

        if (biometrics == null) {
            biometrics = BiometricsTable(
                activityLevel = ActivityLevel.LIGHTLY_ACTIVE,
                birthdate = ZeroHourDate(1989, Calendar.DECEMBER, 13),
                sex = Sex.NOT_SET,
                height = 166.00
            )

            val rowId = dao.insert(biometrics)
            Logger.d("Biometrics inserted at row: $rowId")
        }
    }

    private suspend fun initDay() {
        val dao = database.dayDao()
        val today = today()
        var day = dao.get(today)

        if (day == null) {
            val dayPrior = dao.getDayPriorTo(today)

            day = DayTable(
                id = today,
                kilograms = dayPrior?.kilograms ?: 0.0,
                lastCheckInDate = dayPrior?.lastCheckInDate
            )

            val rowId = dao.insert(day)
            Logger.d("Day: $day inserted at row: $rowId")
        }
    }

    private suspend fun initPreferences() {
        val dao = database.preferencesDao()
        var preferences = dao.get()

        if (preferences == null) {
            preferences = PreferencesTable(
                imperialWeight = imperialWeightType(Locale.getDefault().country) ?: ImperialWeight.POUNDS,
                usesImperialHeight(Locale.getDefault().country),
                usesImperialWeight(Locale.getDefault().country)
            )

            val rowId = dao.insert(preferences)
            Logger.d("Preferences inserted at row: $rowId")
        }
    }

    // Working backward, remove log-less entries that have
    // the same kilogram value as the entry prior
    private fun cleanDayTable() {
        val dayDao = database.dayDao()
        val logItemDao = database.logItemDao()

        val days = dayDao.getAll()
        val logs = logItemDao.getAll()

        val logsByDayId = logs.groupBy { it.dayId }
        val daysToDelete = mutableListOf<DayTable>()

        val today = ZeroHourDate()

        for (day in days) {
            if (day.id == today || day.isCheckInDate()) {
                continue
            }

            val daysLogs = logsByDayId[day.id]

            if (daysLogs.isNullOrEmpty()) {
                daysToDelete.add(day)
            }
        }

        if (daysToDelete.isNotEmpty()) {
            dayDao.deleteAll(daysToDelete)
            Logger.d("Days cleaned: $daysToDelete")
        }
    }
}
