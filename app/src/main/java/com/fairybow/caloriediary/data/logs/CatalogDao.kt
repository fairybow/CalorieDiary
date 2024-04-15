package com.fairybow.caloriediary.data.logs

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao

@Dao
interface CatalogDao : BaseDao<CatalogTableRow> {
    @Query("SELECT * FROM CatalogTableRow")
    fun getAll(): List<CatalogTableRow>
}
