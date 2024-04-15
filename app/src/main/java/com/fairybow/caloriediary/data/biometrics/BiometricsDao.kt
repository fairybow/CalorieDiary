package com.fairybow.caloriediary.data.biometrics

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Dao
interface BiometricsDao : BaseDao<BiometricsTableRow> {
    @Query("SELECT * FROM BiometricsTableRow WHERE id = :id")
    fun get(id: Int): BiometricsTableRow?

    @Query("SELECT activityLevel FROM BiometricsTableRow WHERE id = :id")
    fun getActivityLevel(id: Int): ActivityLevel

    @Query("UPDATE BiometricsTableRow SET activityLevel = :activityLevel WHERE id = :id")
    suspend fun updateActivityLevel(activityLevel: ActivityLevel, id: Int)

    @Query("SELECT birthdate FROM BiometricsTableRow WHERE id = :id")
    fun getBirthdate(id: Int): ZeroHourDate

    @Query("UPDATE BiometricsTableRow SET birthdate = :birthdate WHERE id = :id")
    suspend fun updateBirthdate(birthdate: ZeroHourDate, id: Int)

    @Query("SELECT height FROM BiometricsTableRow WHERE id = :id")
    fun getHeight(id: Int): Double

    @Query("UPDATE BiometricsTableRow SET height = :height WHERE id = :id")
    suspend fun updateHeight(height: Double, id: Int)

    @Query("SELECT sex FROM BiometricsTableRow WHERE id = :id")
    fun getSex(id: Int): Sex

    @Query("UPDATE BiometricsTableRow SET sex = :sex WHERE id = :id")
    suspend fun updateSex(sex: Sex, id: Int)
}
