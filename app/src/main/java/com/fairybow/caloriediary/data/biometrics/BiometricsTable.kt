package com.fairybow.caloriediary.data.biometrics

import androidx.room.Entity
import com.fairybow.caloriediary.data.BaseSingleRowTable
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Entity
data class BiometricsTable(
    var activityLevel: ActivityLevel,
    var birthdate: ZeroHourDate,
    var sex: Sex,
    var height: Double
) : BaseSingleRowTable()
