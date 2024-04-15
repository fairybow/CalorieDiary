package com.fairybow.caloriediary.data.logs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatalogTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var calories: Double,
    var name: String
)
