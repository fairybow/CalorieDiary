package com.fairybow.caloriediary.data.day

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Dao
interface DayDao : BaseDao<DayTable> {
    @Query("SELECT * FROM DayTable WHERE id = :id")
    fun get(id: ZeroHourDate): DayTable?

    @Query("SELECT * FROM DayTable")
    fun getAll(): List<DayTable>

    @Query("SELECT * FROM DayTable WHERE id < :id ORDER BY id DESC LIMIT 1")
    fun getDayPriorTo(id: ZeroHourDate): DayTable?

    @Query("SELECT kilograms FROM DayTable WHERE id = :id")
    fun getKilograms(id: ZeroHourDate = ZeroHourDate()): Double

    @Query("UPDATE DayTable SET kilograms = :kilograms WHERE id = :id")
    suspend fun updateKilograms(kilograms: Double, id: ZeroHourDate = ZeroHourDate())

    @Query("SELECT lastCheckInDate FROM DayTable WHERE id = :id")
    fun getLastCheckInDate(id: ZeroHourDate = ZeroHourDate()): ZeroHourDate?

    @Query("UPDATE DayTable SET lastCheckInDate = :lastCheckInDate WHERE id = :id")
    suspend fun updateLastCheckInDate(lastCheckInDate: ZeroHourDate, id: ZeroHourDate = ZeroHourDate())
}
