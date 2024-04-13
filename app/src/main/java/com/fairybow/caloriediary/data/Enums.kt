package com.fairybow.caloriediary.data

import com.fairybow.caloriediary.tools.capitalizeFirst

enum class ActivityLevel {
    SEDENTARY,
    LIGHTLY_ACTIVE,
    MODERATELY_ACTIVE,
    ACTIVE,
    VERY_ACTIVE
}

enum class ImperialWeight {
    POUNDS,
    STONES,
    STONES_AND_POUNDS
}

enum class Sex {
    NOT_SET,
    FEMALE,
    MALE
}

fun Enum<*>.toSentenceCaseString(): String {
    val sentence = this.name.split("_").joinToString(" ") { it.lowercase() }

    return capitalizeFirst(sentence)
}
