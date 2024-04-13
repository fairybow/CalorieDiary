package com.fairybow.caloriediary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.fairybow.caloriediary.debug.Logger

@Dao
interface BiometricsDao {
    @Query("SELECT * FROM Biometrics LIMIT 1")
    fun getBiometrics(): Biometrics?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiometrics(biometrics: Biometrics): Long

    @Update
    fun updateBiometrics(biometrics: Biometrics)

    @Query("SELECT activityLevel FROM Biometrics LIMIT 1")
    fun getActivityLevel(): ActivityLevel

    @Query("UPDATE Biometrics SET activityLevel = :activityLevel WHERE id = :singletonId")
    suspend fun updateActivityLevel(activityLevel: ActivityLevel, singletonId: Int = SingletonID.id())

    @Query("SELECT birthdate FROM Biometrics LIMIT 1")
    fun getBirthdate(): ZeroHourDate

    @Query("UPDATE Biometrics SET birthdate = :birthdate WHERE id = :singletonId")
    suspend fun updateBirthdate(birthdate: ZeroHourDate, singletonId: Int = SingletonID.id())

    @Query("SELECT sex FROM Biometrics LIMIT 1")
    fun getSex(): Sex

    @Query("UPDATE Biometrics SET sex = :sex WHERE id = :singletonId")
    suspend fun updateSex(sex: Sex, singletonId: Int = SingletonID.id())

    @Query("SELECT height FROM Biometrics LIMIT 1")
    fun getHeight(): Double

    @Query("UPDATE Biometrics SET height = :height WHERE id = :singletonId")
    suspend fun updateHeight(height: Double, singletonId: Int = SingletonID.id())
}

@Dao
interface PreferencesDao {
    @Query("SELECT * FROM Preferences LIMIT 1")
    fun getPreferences(): Preferences?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreferences(preferences: Preferences): Long

    @Update
    fun updatePreferences(preferences: Preferences)

    @Query("SELECT imperialWeight FROM Preferences LIMIT 1")
    fun getImperialWeight(): ImperialWeight

    @Query("UPDATE Preferences SET imperialWeight = :imperialWeight WHERE id = :singletonId")
    suspend fun updateImperialWeight(imperialWeight: ImperialWeight, singletonId: Int = SingletonID.id())

    @Query("SELECT useImperialHeight FROM Preferences LIMIT 1")
    fun getUseImperialHeight(): Boolean

    @Query("UPDATE Preferences SET useImperialHeight = :useImperialHeight WHERE id = :singletonId")
    suspend fun updateUseImperialHeight(useImperialHeight: Boolean, singletonId: Int = SingletonID.id())

    @Query("SELECT useImperialWeight FROM Preferences LIMIT 1")
    fun getUseImperialWeight(): Boolean

    @Query("UPDATE Preferences SET useImperialWeight = :useImperialWeight WHERE id = :singletonId")
    suspend fun updateUseImperialWeight(useImperialWeight: Boolean, singletonId: Int = SingletonID.id())
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM JournalEntry WHERE id = :id")
    fun getJournalEntry(id: ZeroHourDate): JournalEntry?

    @Query("SELECT * FROM JournalEntry WHERE id < :id ORDER BY id DESC LIMIT 1")
    fun getPreviousJournalEntry(id: ZeroHourDate): JournalEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(journalEntry: JournalEntry): Long
    // ^ Was originally not suspend & had no conflict strategy--may cause issues?

    @Delete
    fun deleteJournalEntry(journalEntry: JournalEntry)

    @Delete
    fun deleteJournalEntries(journalEntries: List<JournalEntry>)

    @Query("SELECT kilograms FROM JournalEntry WHERE id = :id")
    fun getKilograms(id: ZeroHourDate = ZeroHourDate()): Double

    @Query("UPDATE JournalEntry SET kilograms = :kilograms WHERE id = :id")
    suspend fun updateKilograms(kilograms: Double, id: ZeroHourDate = ZeroHourDate())

    @Query("SELECT lastCheckInDate FROM JournalEntry WHERE id = :id")
    fun getLastCheckInDate(id: ZeroHourDate = ZeroHourDate()): ZeroHourDate?

    @Query("UPDATE JournalEntry SET lastCheckInDate = :lastCheckInDate WHERE id = :id")
    suspend fun updateLastCheckInDate(lastCheckInDate: ZeroHourDate, id: ZeroHourDate = ZeroHourDate())

    @Insert
    fun insertLogEntry(logEntry: LogEntry)

    @Query("SELECT * FROM JournalEntry")
    fun getAllJournalEntries(): List<JournalEntry>

    @Query("SELECT * FROM LogEntry WHERE journalEntryId = :journalEntryId")
    fun getLogEntriesForJournalEntry(journalEntryId: ZeroHourDate): List<LogEntry>

    @Query("SELECT * FROM LogEntry")
    fun getAllLogEntries(): List<LogEntry>

    // Working backward, remove log-less entries that have
    // the same kilogram value as the entry prior
    @Transaction
    fun clean() {
        val journalEntries = getAllJournalEntries()
        val allLogs = getAllLogEntries()
        val logsByJournalEntryId = allLogs.groupBy { it.journalEntryId }
        val entriesToDelete = mutableListOf<JournalEntry>()

        val today = ZeroHourDate()

        for (entry in journalEntries) {
            if (entry.id == today || entry.isCheckInDate()) {
                continue
            }

            val logs = logsByJournalEntryId[entry.id]

            if (logs.isNullOrEmpty()) {
                entriesToDelete.add(entry)
            }
        }

        if (entriesToDelete.isNotEmpty()) {
            deleteJournalEntries(entriesToDelete)
            Logger.d("Journal entries cleaned: $entriesToDelete")
        }
    }
}
