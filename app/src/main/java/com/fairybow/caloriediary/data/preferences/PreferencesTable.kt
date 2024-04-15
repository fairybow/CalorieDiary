package com.fairybow.caloriediary.data.preferences

import androidx.room.Entity
import com.fairybow.caloriediary.data.BaseSingleRowTable
import com.fairybow.caloriediary.utilities.ImperialWeight

@Entity
data class PreferencesTable(
    var imperialWeight: ImperialWeight,
    var useImperialHeight: Boolean,
    var useImperialWeight: Boolean
) : BaseSingleRowTable()
