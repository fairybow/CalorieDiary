package com.fairybow.caloriediary.data.logs

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.utilities.ZeroHourDate

@Dao
interface LogItemDao : BaseDao<LogItemTable> {
    @Query("SELECT * FROM LogItemTable WHERE dayId = :dayId")
    fun get(dayId: ZeroHourDate): List<LogItemTable>

    @Query("SELECT * FROM LogItemTable")
    fun getAll(): List<LogItemTable>
}
