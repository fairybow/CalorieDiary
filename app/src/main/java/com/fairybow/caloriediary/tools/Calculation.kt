package com.fairybow.caloriediary.tools

import com.fairybow.caloriediary.data.ZeroHourDate
import java.util.Calendar
import java.util.Date

// TODO: Add to ZHD

fun midpoint(first: Double, last: Double): Double {
    return (first + last) / 2
}

fun getCurrentAge(birthdate: Date): Int {
    val birthdateCalendar: Calendar = Calendar.getInstance().apply { time = birthdate }
    val today: Calendar = Calendar.getInstance()
    var age = today.get(Calendar.YEAR) - birthdateCalendar.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < birthdateCalendar.get(Calendar.DAY_OF_YEAR)) {
        age-- // Subtract one from age if birthday hasn't occurred yet this year
    }

    return age
}

fun getCurrentAge(birthdate: ZeroHourDate): Int {
    return getCurrentAge(birthdate.toDate())
}

fun epochYearsAway(n: Int): Long {
    val calendar = Calendar.getInstance().apply { time = Date() }
    calendar.add(Calendar.YEAR, n)

    return calendar.timeInMillis
}

fun daysAway(n: Int): Date {
    val calendar = Calendar.getInstance().apply { time = Date() }
    calendar.add(Calendar.DAY_OF_MONTH, n)

    return calendar.time
}
