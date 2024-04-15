package com.fairybow.caloriediary.utilities

import androidx.room.TypeConverter

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

class Converters {
    @TypeConverter
    fun fromSex(value: Sex): String {
        return value.name
    }

    @TypeConverter
    fun toSex(value: String): Sex {
        return Sex.valueOf(value)
    }

    @TypeConverter
    fun fromActivityLevel(value: ActivityLevel): String {
        return value.name
    }

    @TypeConverter
    fun toActivityLevel(value: String): ActivityLevel {
        return ActivityLevel.valueOf(value)
    }

    @TypeConverter
    fun fromImperialWeight(value: ImperialWeight): String {
        return value.name
    }

    @TypeConverter
    fun toImperialWeight(value: String): ImperialWeight {
        return ImperialWeight.valueOf(value)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): ZeroHourDate? {
        return value?.let { ZeroHourDate(it) }
    }

    @TypeConverter
    fun zeroHourDateToTimestamp(date: ZeroHourDate?): Long? {
        return date?.toEpoch()
    }
}
