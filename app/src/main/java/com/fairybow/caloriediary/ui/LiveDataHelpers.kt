package com.fairybow.caloriediary.ui

import com.fairybow.caloriediary.debug.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun <T> getLiveData(dataGetter: suspend () -> T): T {
    val value = withContext(Dispatchers.IO) {
        dataGetter()
    }

    Logger.d("getLiveData: $value value retrieved")

    return value
}

suspend fun <T> getNullableLiveData(dataGetter: suspend () -> T?): T? {
    val value = withContext(Dispatchers.IO) {
        dataGetter()
    }

    Logger.d("getNullableLiveData: $value value retrieved")

    return value
}


fun <T> setLiveData(scope: CoroutineScope, value: T, dataSetter: suspend (T) -> Unit, liveDataSetter: (T) -> Unit) {
    scope.launch {
        withContext(Dispatchers.IO) {
            dataSetter(value)
        }

        liveDataSetter(value)

        Logger.d("setLiveData: $value value set")
    }
}
