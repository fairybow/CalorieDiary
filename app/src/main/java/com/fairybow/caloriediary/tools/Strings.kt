package com.fairybow.caloriediary.tools

import java.text.DecimalFormat
import kotlin.math.roundToInt

fun capitalizeFirst(string: String): String {
    return string.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun prettyCentimeters(centimeters: Double): String {
    return "${centimeters.roundToInt()} $UNIT_CM"
}

fun prettyFeetInches(centimeters: Double): String {
    val height = toFeetInches(centimeters)

    return "${height.first} $UNIT_FT ${height.second.roundToInt()} $UNIT_IN"
}

fun prettify(value: Number): String {
    val df = if (value is Int) DecimalFormat(DF_INT) else DecimalFormat(DF_DOUBLE)

    return df.format(value)
}

/*fun prettyKCalories(kCalories: Double): String {
    return "${kCalories.roundToInt()} $UNIT_RATE_KC"
}*/

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

fun toOrdinatedString(int: Int): String {
    val number = int.toString()
    val lastDigit = int % 10
    val lastTwoDigits = int % 100

    return when {
        lastTwoDigits in 11..13 -> number + "th"
        lastDigit == 1 -> number + "st"
        lastDigit == 2 -> number + "nd"
        lastDigit == 3 -> number + "rd"
        else -> number + "th"
    }
}

fun toProperCase(sentence: String): String {
    return sentence.split(" ").joinToString(" ") { capitalizeFirst(it) }
}
