package com.fairybow.caloriediary.tools

import com.fairybow.caloriediary.data.ImperialWeight

fun imperialWeightType(countryCode: String): ImperialWeight? {
    return when {
        !usesImperialWeight(countryCode) -> null
        countryCode == UK || countryCode == IRELAND -> ImperialWeight.STONES_AND_POUNDS
        else -> ImperialWeight.POUNDS
    }
}

fun toKilograms(pounds: Double): Double {
    return pounds / KG_LB_FACTOR
}

fun toKilogramsFromStones(stones: Double): Double {
    return stones * ST_KG_FACTOR
}

fun toPounds(kilograms: Double): Double {
    return kilograms * KG_LB_FACTOR
}

fun toStones(kilograms: Double): Double {
    return kilograms / ST_KG_FACTOR
}

fun toStonesFromPounds(pounds: Double): Double {
    return pounds / ST_LB_FACTOR
}

fun toStonesPounds(kilograms: Double): Pair<Int, Double> {
    val total = toStones(kilograms).toInt()
    val remaining = kilograms - (total * ST_KG_FACTOR)

    return Pair(total, toPounds(remaining))
}

fun usesImperialWeight(countryCode: String): Boolean {
    return usesImperialCommonly(countryCode) ||
            countryCode == EGYPT ||
            countryCode == LEBANON ||
            countryCode == SOUTH_SUDAN ||
            countryCode == SUDAN ||
            countryCode == SYRIA
}
