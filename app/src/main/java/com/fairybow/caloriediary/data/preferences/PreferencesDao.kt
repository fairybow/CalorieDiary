package com.fairybow.caloriediary.data.preferences

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.utilities.ImperialWeight

@Dao
interface PreferencesDao : BaseDao<PreferencesTableRow> {
    @Query("SELECT * FROM PreferencesTableRow WHERE id = :id")
    fun get(id: Int): PreferencesTableRow?

    @Query("SELECT imperialWeight FROM PreferencesTableRow WHERE id = :id")
    fun getImperialWeightType(id: Int): ImperialWeight

    @Query("UPDATE PreferencesTableRow SET imperialWeight = :imperialWeight WHERE id = :id")
    suspend fun updateImperialWeightType(imperialWeight: ImperialWeight, id: Int)

    @Query("SELECT useImperialHeight FROM PreferencesTableRow WHERE id = :id")
    fun getUseImperialHeight(id: Int): Boolean

    @Query("UPDATE PreferencesTableRow SET useImperialHeight = :useImperialHeight WHERE id = :id")
    suspend fun updateUseImperialHeight(useImperialHeight: Boolean, id: Int)

    @Query("SELECT useImperialWeight FROM PreferencesTableRow WHERE id = :id")
    fun getUseImperialWeight(id: Int): Boolean

    @Query("UPDATE PreferencesTableRow SET useImperialWeight = :useImperialWeight WHERE id = :id")
    suspend fun updateUseImperialWeight(useImperialWeight: Boolean, id: Int)
}
