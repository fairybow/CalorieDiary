package com.fairybow.caloriediary.tools

import com.fairybow.caloriediary.data.ActivityLevel
import com.fairybow.caloriediary.data.Sex

fun mifflinStJeor(weight: Double?, height: Double?, age: Int?, sex: Sex?, activityLevel: ActivityLevel?): Double {
    if (weight == null || height == null || age == null || sex == null || activityLevel == null) {
        return 0.0
    }

    val activityAdj = when (activityLevel) {
        ActivityLevel.SEDENTARY -> 1.2
        ActivityLevel.LIGHTLY_ACTIVE -> 1.375
        ActivityLevel.MODERATELY_ACTIVE -> 1.55
        ActivityLevel.ACTIVE -> 1.725
        ActivityLevel.VERY_ACTIVE -> 1.9
    }

    val formula = { sexAdj: Double ->
        ((MSJ_KG_FACTOR * weight) + (MSJ_CM_FACTOR * height) - (MSJ_AGE_FACTOR * age) + sexAdj) * activityAdj
    }

    return when (sex) {
        Sex.FEMALE -> formula(MSJ_F_ADJ)
        Sex.MALE -> formula(MSJ_M_ADJ)
        Sex.NOT_SET -> (formula(MSJ_F_ADJ) + formula(MSJ_M_ADJ) / 2)
    }
}

/*fun toKilojoules(kilocalories: Double): Double {
    return kilocalories * KC_KJ_FACTOR
}*/
