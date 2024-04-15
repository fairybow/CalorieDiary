package com.fairybow.caloriediary.data.preferences

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fairybow.caloriediary.utilities.ImperialWeight

@Entity
data class PreferencesTableRow(
    @PrimaryKey var id: Int,
    var imperialWeight: ImperialWeight,
    var useImperialHeight: Boolean,
    var useImperialWeight: Boolean
)
