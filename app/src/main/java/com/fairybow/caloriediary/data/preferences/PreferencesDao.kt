package com.fairybow.caloriediary.data.preferences

import androidx.room.Dao
import androidx.room.Query
import com.fairybow.caloriediary.data.BaseDao
import com.fairybow.caloriediary.data.SINGLETON_ID
import com.fairybow.caloriediary.utilities.ImperialWeight

@Dao
interface PreferencesDao : BaseDao<PreferencesTable> {
    @Query("SELECT * FROM PreferencesTable WHERE id = $SINGLETON_ID")
    fun get(): PreferencesTable?

    @Query("SELECT imperialWeight FROM PreferencesTable WHERE id = $SINGLETON_ID")
    fun getImperialWeightType(): ImperialWeight

    @Query("UPDATE PreferencesTable SET imperialWeight = :imperialWeight WHERE id = $SINGLETON_ID")
    suspend fun updateImperialWeightType(imperialWeight: ImperialWeight)

    @Query("SELECT useImperialHeight FROM PreferencesTable WHERE id = $SINGLETON_ID")
    fun getUseImperialHeight(): Boolean

    @Query("UPDATE PreferencesTable SET useImperialHeight = :useImperialHeight WHERE id = $SINGLETON_ID")
    suspend fun updateUseImperialHeight(useImperialHeight: Boolean)

    @Query("SELECT useImperialWeight FROM PreferencesTable WHERE id = $SINGLETON_ID")
    fun getUseImperialWeight(): Boolean

    @Query("UPDATE PreferencesTable SET useImperialWeight = :useImperialWeight WHERE id = $SINGLETON_ID")
    suspend fun updateUseImperialWeight(useImperialWeight: Boolean)
}
