package com.fairybow.caloriediary.utilities

fun toCentimeters(inches: Double): Double {
    return inches * CM_IN_FACTOR
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

fun toInches(centimeters: Double): Double {
    return centimeters / CM_IN_FACTOR
}

fun toInchesFromFeet(feet: Double): Double {
    return feet * FEET_IN_FACTOR
}

fun usesImperialHeight(countryCode: String): Boolean {
    return usesImperialCommonly(countryCode)
}
