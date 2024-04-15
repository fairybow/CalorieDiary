package com.fairybow.caloriediary.data.biometrics

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.data.SINGLETON_ID
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Dao
interface BiometricsDao : BaseDao<BiometricsTable> {
    @Query("SELECT * FROM BiometricsTable WHERE id = $SINGLETON_ID")
    fun get(): BiometricsTable?

    @Query("SELECT activityLevel FROM BiometricsTable WHERE id = $SINGLETON_ID")
    fun getActivityLevel(): ActivityLevel

    @Query("UPDATE BiometricsTable SET activityLevel = :activityLevel WHERE id = $SINGLETON_ID")
    suspend fun updateActivityLevel(activityLevel: ActivityLevel)

    @Query("SELECT birthdate FROM BiometricsTable WHERE id = $SINGLETON_ID")
    fun getBirthdate(): ZeroHourDate

    @Query("UPDATE BiometricsTable SET birthdate = :birthdate WHERE id = $SINGLETON_ID")
    suspend fun updateBirthdate(birthdate: ZeroHourDate)

    @Query("SELECT height FROM BiometricsTable WHERE id = $SINGLETON_ID")
    fun getHeight(): Double

    @Query("UPDATE BiometricsTable SET height = :height WHERE id = $SINGLETON_ID")
    suspend fun updateHeight(height: Double)

    @Query("SELECT sex FROM BiometricsTable WHERE id = $SINGLETON_ID")
    fun getSex(): Sex

    @Query("UPDATE BiometricsTable SET sex = :sex WHERE id = $SINGLETON_ID")
    suspend fun updateSex(sex: Sex)
}
