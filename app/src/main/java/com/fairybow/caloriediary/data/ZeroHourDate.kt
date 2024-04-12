package com.fairybow.caloriediary.data

import com.fairybow.caloriediary.tools.toOrdinatedString
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

class ZeroHourDate {
    constructor(date: Date = Date()) {
        this._date = setCalendar(date).time
    }

    constructor(year: Int, month: Int, day: Int) : this(GregorianCalendar(year, month, day))

    constructor(timestamp: Long) : this(Date(timestamp))

    constructor(calendar: Calendar) : this(calendar.time)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ZeroHourDate

        return this._date == other._date
    }

    override fun hashCode(): Int {
        return _date.time.hashCode()
    }

    override fun toString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_date)
    }

    fun toColloquialString(includeYearIfSameYear: Boolean = false): String {
        return when (this) {
            today() -> {
                "today"
            }
            yesterday() -> {
                "yesterday"
            }
            tomorrow() -> {
                "tomorrow"
            }
            else -> {
                val delimiter = ", "
                val day = toOrdinatedString(day())
                var str = "${dayName()}$delimiter${monthName()} $day"
                val year = year()

                if (year != ZeroHourDate().year() || includeYearIfSameYear) {
                    str += "$delimiter$year"
                }

                str
            }
        }
    }

    fun toDate(): Date {
        return _date
    }

    fun toEpoch(): Long {
        return _date.time
    }

    fun year(): Int {
        return _calendar.get(Calendar.YEAR)
    }

    fun month(): Int {
        return _calendar.get(Calendar.MONTH)
    }

    fun day(): Int {
        return _calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun monthName(): String {
        return DateFormatSymbols().months[_calendar.get(Calendar.MONTH)]
    }

    fun dayName(): String {
        val format = SimpleDateFormat("EEEE", Locale.getDefault())

        return format.format(_calendar.time)
    }

    companion object {
        private fun daysAway(n: Int): Date {
            val calendar = Calendar.getInstance().apply { time = Date() }
            calendar.add(Calendar.DAY_OF_MONTH, n)

            return calendar.time
        }

        fun yesterday(): ZeroHourDate {
            return ZeroHourDate(daysAway(-1))
        }

        fun tomorrow(): ZeroHourDate {
            return ZeroHourDate(daysAway(1))
        }

        fun today(): ZeroHourDate {
            return ZeroHourDate()
        }
    }

    private val _date: Date

    private val _calendar: Calendar
        get() = setCalendar(_date)

    private val _threadLocalCalendar: ThreadLocal<Calendar> = object : ThreadLocal<Calendar>() {
        override fun initialValue(): Calendar {
            return Calendar.getInstance()
        }
    }

    private fun setCalendar(date: Date): Calendar {
        return _threadLocalCalendar.get()!!.apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}
