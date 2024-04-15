package com.fairybow.caloriediary.data.logs

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fairybow.caloriediary.data.day.DayTableRow
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = DayTableRow::class,
            parentColumns = ["id"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LogTableRow(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var calories: Double,
    val dayId: ZeroHourDate,
    var name: String?,
    //var catalogId: Int?
)
