package com.fairybow.caloriediary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fairybow.caloriediary.data.biometrics.BiometricsTableRow
import com.fairybow.caloriediary.data.biometrics.BiometricsDao
import com.fairybow.caloriediary.data.day.DayTableRow
import com.fairybow.caloriediary.data.day.DayDao
import com.fairybow.caloriediary.data.logs.CatalogDao
import com.fairybow.caloriediary.data.logs.CatalogTableRow
import com.fairybow.caloriediary.data.logs.LogDao
import com.fairybow.caloriediary.data.logs.LogTableRow
import com.fairybow.caloriediary.data.preferences.PreferencesTableRow
import com.fairybow.caloriediary.data.preferences.PreferencesDao
import com.fairybow.caloriediary.utilities.Converters

@Database(
    entities = [
        BiometricsTableRow::class,
        CatalogTableRow::class,
        DayTableRow::class,
        LogTableRow::class,
        PreferencesTableRow::class
               ]
    , version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun biometricsDao(): BiometricsDao
    abstract fun catalogDao(): CatalogDao
    abstract fun dayDao(): DayDao
    abstract fun logDao(): LogDao
    abstract fun preferencesDao(): PreferencesDao
}
