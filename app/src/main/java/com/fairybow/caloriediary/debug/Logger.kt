package com.fairybow.caloriediary.debug

import android.util.Log
import com.fairybow.caloriediary.BuildConfig

object Logger {
    private const val TAG = "CalorieDiaryDebug"

    fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }
}
