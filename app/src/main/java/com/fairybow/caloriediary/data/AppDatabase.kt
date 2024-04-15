package com.fairybow.caloriediary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fairybow.caloriediary.data.biometrics.BiometricsTable
import com.fairybow.caloriediary.data.biometrics.BiometricsDao
import com.fairybow.caloriediary.data.day.DayTable
import com.fairybow.caloriediary.data.day.DayDao
import com.fairybow.caloriediary.data.logs.CatalogDao
import com.fairybow.caloriediary.data.logs.CatalogTable
import com.fairybow.caloriediary.data.logs.LogItemDao
import com.fairybow.caloriediary.data.logs.LogItemTable
import com.fairybow.caloriediary.data.preferences.PreferencesTable
import com.fairybow.caloriediary.data.preferences.PreferencesDao
import com.fairybow.caloriediary.utilities.Converters

@Database(
    entities = [
        BiometricsTable::class,
        CatalogTable::class,
        DayTable::class,
        LogItemTable::class,
        PreferencesTable::class
               ]
    , version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun biometricsDao(): BiometricsDao
    abstract fun catalogDao(): CatalogDao
    abstract fun dayDao(): DayDao
    abstract fun logItemDao(): LogItemDao
    abstract fun preferencesDao(): PreferencesDao
}
