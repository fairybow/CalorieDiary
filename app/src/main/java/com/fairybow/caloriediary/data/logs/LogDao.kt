package com.fairybow.caloriediary.data.logs

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Dao
interface LogDao : BaseDao<LogTableRow> {
    @Query("SELECT * FROM LogTableRow")
    fun getAll(): List<LogTableRow>

    @Query("SELECT * FROM LogTableRow WHERE dayId = :dayId")
    fun getAllFrom(dayId: ZeroHourDate): List<LogTableRow>
}
