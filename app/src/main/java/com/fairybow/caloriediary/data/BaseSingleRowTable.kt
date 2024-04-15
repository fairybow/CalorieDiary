package com.fairybow.caloriediary.data

import androidx.room.PrimaryKey

const val SINGLETON_ID = 1

open class BaseSingleRowTable(
    @PrimaryKey var id: Int = SINGLETON_ID
)
