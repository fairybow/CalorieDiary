package com.fairybow.caloriediary.data

import com.fairybow.caloriediary.data.biometrics.BiometricsTableRow
import com.fairybow.caloriediary.data.day.DayTableRow
import com.fairybow.caloriediary.data.preferences.PreferencesTableRow
import com.fairybow.caloriediary.debug.Logger
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.ImperialWeight
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate
import com.fairybow.caloriediary.utilities.imperialWeightType
import com.fairybow.caloriediary.utilities.today
import com.fairybow.caloriediary.utilities.usesImperialHeight
import com.fairybow.caloriediary.utilities.usesImperialWeight
import java.util.Calendar
import java.util.Locale

class Repository(database: AppDatabase) {
    private val singleRowTableId = 1

    private val biometricsDao = database.biometricsDao()
    //private val catalogDao = database.catalogDao()
    private val dayDao = database.dayDao()
    private val logDao = database.logDao()
    private val preferencesDao = database.preferencesDao()

    suspend fun initializeTables() {
        initSingleRowBiometrics()
        initSingleRowPreferences()

        initDay()

        cleanDayTable()
    }

    // Biometrics

    private suspend fun initSingleRowBiometrics() {
        var biometrics = biometricsDao.get(singleRowTableId)

        if (biometrics == null) {
            biometrics = BiometricsTableRow(
                id = singleRowTableId,
                activityLevel = ActivityLevel.LIGHTLY_ACTIVE,
                birthdate = ZeroHourDate(1989, Calendar.DECEMBER, 13),
                sex = Sex.NOT_SET,
                height = 166.00
            )

            val rowId = biometricsDao.insert(biometrics)
            Logger.d("Biometrics inserted at row: $rowId")
        }
    }

    fun getActivityLevel(): ActivityLevel {
        return biometricsDao.getActivityLevel(singleRowTableId)
    }

    suspend fun setActivityLevel(activityLevel: ActivityLevel) {
        biometricsDao.updateActivityLevel(activityLevel, singleRowTableId)
    }

    fun getBirthdate(): ZeroHourDate {
        return biometricsDao.getBirthdate(singleRowTableId)
    }

    suspend fun setBirthdate(birthdate: ZeroHourDate) {
        biometricsDao.updateBirthdate(birthdate, singleRowTableId)
    }

    fun getHeight(): Double {
        return biometricsDao.getHeight(singleRowTableId)
    }

    suspend fun setHeight(height: Double) {
        biometricsDao.updateHeight(height, singleRowTableId)
    }

    fun getSex(): Sex {
        return biometricsDao.getSex(singleRowTableId)
    }

    suspend fun setSex(sex: Sex) {
        biometricsDao.updateSex(sex, singleRowTableId)
    }

    // Day

    private suspend fun initDay() {
        val today = today()
        var day = dayDao.get(today)

        if (day == null) {
            val dayPrior = dayDao.getDayPriorTo(today)

            day = DayTableRow(
                id = today,
                kilograms = dayPrior?.kilograms ?: 0.0,
                lastCheckInDate = dayPrior?.lastCheckInDate
            )

            val rowId = dayDao.insert(day)
            Logger.d("Day: $day inserted at row: $rowId")
        }
    }

    fun getKilograms(): Double {
        return dayDao.getKilograms(today())
    }

    suspend fun setKilograms(kilograms: Double) {
        dayDao.updateKilograms(kilograms, today())
    }

    fun getLastCheckInDate(): ZeroHourDate? {
        return dayDao.getLastCheckInDate(today())
    }

    suspend fun setLastCheckInDate(lastCheckInDate: ZeroHourDate) {
        dayDao.updateLastCheckInDate(lastCheckInDate, today())
    }

    // Working backward, remove log-less entries that have
    // the same kilogram value as the entry prior
    private fun cleanDayTable() {
        val days = dayDao.getAll()
        val logs = logDao.getAll()

        val logsByDayId = logs.groupBy { it.dayId }
        val daysToDelete = mutableListOf<DayTableRow>()

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
            Logger.d("Day table cleaned: $daysToDelete")
        }
    }

    // Preferences

    private suspend fun initSingleRowPreferences() {
        var preferences = preferencesDao.get(singleRowTableId)

        if (preferences == null) {
            preferences = PreferencesTableRow(
                id = singleRowTableId,
                imperialWeight = imperialWeightType(Locale.getDefault().country) ?: ImperialWeight.POUNDS,
                useImperialHeight = usesImperialHeight(Locale.getDefault().country),
                useImperialWeight = usesImperialWeight(Locale.getDefault().country)
            )

            val rowId = preferencesDao.insert(preferences)
            Logger.d("Preferences inserted at row: $rowId")
        }
    }

    fun getImperialWeightType(): ImperialWeight {
        return preferencesDao.getImperialWeightType(singleRowTableId)
    }

    suspend fun setImperialWeightType(imperialWeight: ImperialWeight) {
        preferencesDao.updateImperialWeightType(imperialWeight, singleRowTableId)
    }

    fun getUseImperialHeight(): Boolean {
        return preferencesDao.getUseImperialHeight(singleRowTableId)
    }

    suspend fun setUseImperialHeight(useImperialHeight: Boolean) {
        preferencesDao.updateUseImperialHeight(useImperialHeight, singleRowTableId)
    }

    fun getUseImperialWeight(): Boolean {
        return preferencesDao.getUseImperialWeight(singleRowTableId)
    }

    suspend fun setUseImperialWeight(useImperialWeight: Boolean) {
        preferencesDao.updateUseImperialWeight(useImperialWeight, singleRowTableId)
    }
}
