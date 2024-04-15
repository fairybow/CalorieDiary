package com.fairybow.caloriediary.data.biometrics

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Entity
data class BiometricsTableRow(
    @PrimaryKey var id: Int,
    var activityLevel: ActivityLevel,
    var birthdate: ZeroHourDate,
    var sex: Sex,
    var height: Double
)
