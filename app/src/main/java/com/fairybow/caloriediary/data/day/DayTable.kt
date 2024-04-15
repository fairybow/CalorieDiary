package com.fairybow.caloriediary.data.day

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Entity(indices = [Index("id")])
data class DayTable(
    @PrimaryKey val id: ZeroHourDate,
    var kilograms: Double,
    var lastCheckInDate: ZeroHourDate?
) {
    fun isCheckInDate(): Boolean {
        return id == (lastCheckInDate ?: return false)
    }
}
