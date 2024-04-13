package com.fairybow.caloriediary.tools

import com.fairybow.caloriediary.data.ActivityLevel
import com.fairybow.caloriediary.data.ImperialWeight
import com.fairybow.caloriediary.data.Sex
import java.text.DecimalFormat
import kotlin.math.roundToInt

const val CM_IN_FACTOR = 2.54
const val FEET_IN_FACTOR = 12.0
const val FEET_CM_FACTOR = 30.48
const val KG_LB_FACTOR = 2.20462262
const val ST_KG_FACTOR = 6.35029317
const val ST_LB_FACTOR = 14
//const val KC_KJ_FACTOR = 4.184

const val AUSTRALIA = "AU"
const val CANADA = "CA"
const val EGYPT = "EG"
const val INDIA = "IN"
const val IRELAND = "IE"
const val LEBANON = "LB"
const val LIBERIA = "LR"
const val MYANMAR = "MM"
const val SOUTH_AFRICA = "ZA"
const val SOUTH_SUDAN = "SS"
const val SUDAN = "SD"
const val SYRIA = "SY"
const val UK = "GB"
const val US = "US"

const val UNIT_CM = "cm"
const val UNIT_FT = "ft"
const val UNIT_IN = "in"
const val UNIT_KG = "kg"
const val UNIT_LB = "lb"
const val UNIT_ST = "st"
const val UNIT_RATE_KC = "kcal/day"
//const val UNIT_RATE_KJ = "kJ/day"

const val MSJ_KG_FACTOR = 10.0
const val MSJ_CM_FACTOR = 6.25
const val MSJ_AGE_FACTOR = 5.0
const val MSJ_F_ADJ = -161.0
const val MSJ_M_ADJ = 5.0

// Height

fun toCentimeters(inches: Double): Double {
    return inches * CM_IN_FACTOR
}

fun toInches(centimeters: Double): Double {
    return centimeters / CM_IN_FACTOR
}

fun toInchesFromFeet(feet: Double): Double {
    return feet * FEET_IN_FACTOR
}

fun toFeet(centimeters: Double): Double {
    return centimeters / FEET_CM_FACTOR
}

fun toFeetFromInches(inches: Double): Double {
    return inches / FEET_IN_FACTOR
}

fun toFeetInches(centimeters: Double): Pair<Int, Double> {
    val total = toFeet(centimeters).toInt()
    val remaining = centimeters - (total * FEET_CM_FACTOR)

    return Pair(total, toInches(remaining))
}

fun usesImperial(countryCode: String): Boolean {
    return countryCode == LIBERIA ||
            countryCode == MYANMAR ||
            countryCode == US
}

fun usesImperialCommonly(countryCode: String): Boolean {
    return usesImperial(countryCode) ||
            countryCode == AUSTRALIA ||
            countryCode == CANADA ||
            countryCode == INDIA ||
            countryCode == IRELAND ||
            countryCode == SOUTH_AFRICA ||
            countryCode == UK
}

fun usesImperialHeight(countryCode: String): Boolean {
    return usesImperialCommonly(countryCode)
}

fun usesImperialWeight(countryCode: String): Boolean {
    return usesImperialCommonly(countryCode) ||
            countryCode == EGYPT ||
            countryCode == LEBANON ||
            countryCode == SOUTH_SUDAN ||
            countryCode == SUDAN ||
            countryCode == SYRIA
}

// Weight

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

fun imperialWeightType(countryCode: String): ImperialWeight? {
    return when {
        !usesImperialWeight(countryCode) -> null
        countryCode == UK || countryCode == IRELAND -> ImperialWeight.STONES_AND_POUNDS
        else -> ImperialWeight.POUNDS
    }
}

/*fun toKilojoules(kilocalories: Double): Double {
    return kilocalories * KC_KJ_FACTOR
}*/

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
        Sex.NOT_SET -> midpoint(
            formula(MSJ_F_ADJ),
            formula(MSJ_M_ADJ)
        )
    }
}

// Strings

fun prettyCentimeters(centimeters: Double): String {
    return "${centimeters.roundToInt()} $UNIT_CM"
}

fun prettyFeetInches(centimeters: Double): String {
    val height = toFeetInches(centimeters)

    return "${height.first} $UNIT_FT ${height.second.roundToInt()} $UNIT_IN"
}

fun prettify(value: Double): String {
    val df = DecimalFormat("#,###.##")

    return df.format(value)
}

fun prettify(value: Int): String {
    val df = DecimalFormat("#,###")

    return df.format(value)
}

fun prettyKilograms(kilograms: Double): String {
    return "${prettify(kilograms)} $UNIT_KG"
}

fun prettyPounds(kilograms: Double): String {
    return "${prettify(toPounds(kilograms))} $UNIT_LB"
}

fun prettyStones(kilograms: Double): String {
    return "${prettify(toStones(kilograms))} $UNIT_ST"
}

fun prettyStonesPounds(kilograms: Double): String {
    val weight = toStonesPounds(kilograms)

    return "${weight.first} $UNIT_ST ${prettify(weight.second)} $UNIT_LB"
}

/*fun prettyKCalories(kCalories: Double): String {
    return "${kCalories.roundToInt()} $UNIT_RATE_KC"
}*/
