package com.fairybow.caloriediary.tools

fun capitalizeFirst(string: String): String {
    return string.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun toProperCase(sentence: String): String {
    return sentence.split(" ").joinToString(" ") { capitalizeFirst(it) }
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
