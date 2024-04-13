package com.fairybow.caloriediary.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fairybow.caloriediary.tools.imperialWeightType
import com.fairybow.caloriediary.tools.usesImperialHeight
import com.fairybow.caloriediary.tools.usesImperialWeight
import java.util.Calendar
import java.util.Locale

object SingletonID {
    fun id(): Int {
        return 1
    }
}

@Entity
data class Biometrics(
    @PrimaryKey val id: Int = SingletonID.id(),
    var activityLevel: ActivityLevel = ActivityLevel.LIGHTLY_ACTIVE,
    var birthdate: ZeroHourDate = ZeroHourDate(1989, Calendar.DECEMBER, 13),
    var sex: Sex = Sex.NOT_SET,
    var height: Double = 166.00
)

@Entity
data class Preferences(
    @PrimaryKey val id: Int = SingletonID.id(),
    var imperialWeight: ImperialWeight = imperialWeightType(Locale.getDefault().country) ?: ImperialWeight.POUNDS,
    var useImperialHeight: Boolean = usesImperialHeight(Locale.getDefault().country),
    var useImperialWeight: Boolean = usesImperialWeight(Locale.getDefault().country)
)

@Entity(indices = [Index("id")])
data class JournalEntry(
    @PrimaryKey val id: ZeroHourDate = ZeroHourDate(),
    var kilograms: Double,
    var lastCheckInDate: ZeroHourDate?
) {
    fun isCheckInDate(): Boolean {
        return id == (lastCheckInDate ?: return false)
    }
}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = JournalEntry::class,
            parentColumns = ["id"],
            childColumns = ["journalEntryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LogEntry(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var calories: Double,
    val journalEntryId: ZeroHourDate,
    var name: String
)
