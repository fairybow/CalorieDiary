package com.fairybow.caloriediary.data.day

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Dao
interface DayDao : BaseDao<DayTableRow> {
    @Query("SELECT * FROM DayTableRow WHERE id = :id")
    fun get(id: ZeroHourDate): DayTableRow?

    @Query("SELECT * FROM DayTableRow")
    fun getAll(): List<DayTableRow>

    @Query("SELECT * FROM DayTableRow WHERE id < :id ORDER BY id DESC LIMIT 1")
    fun getDayPriorTo(id: ZeroHourDate): DayTableRow?

    @Query("SELECT kilograms FROM DayTableRow WHERE id = :id")
    fun getKilograms(id: ZeroHourDate): Double

    @Query("UPDATE DayTableRow SET kilograms = :kilograms WHERE id = :id")
    suspend fun updateKilograms(kilograms: Double, id: ZeroHourDate)

    @Query("SELECT lastCheckInDate FROM DayTableRow WHERE id = :id")
    fun getLastCheckInDate(id: ZeroHourDate): ZeroHourDate?

    @Query("UPDATE DayTableRow SET lastCheckInDate = :lastCheckInDate WHERE id = :id")
    suspend fun updateLastCheckInDate(lastCheckInDate: ZeroHourDate, id: ZeroHourDate)
}
