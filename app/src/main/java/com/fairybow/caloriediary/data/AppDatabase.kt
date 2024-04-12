package com.fairybow.caloriediary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Biometrics::class,
        JournalEntry::class,
        LogEntry::class,
        Preferences::class
               ]
    , version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun biometricsDao(): BiometricsDao
    abstract fun journalDao(): JournalDao
    abstract fun preferencesDao(): PreferencesDao
}
