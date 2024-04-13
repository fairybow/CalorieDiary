package com.fairybow.caloriediary.ui

import com.fairybow.caloriediary.debug.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun <T> getDaoData(dataGetter: suspend () -> T): T {
    val value = withContext(Dispatchers.IO) {
        dataGetter()
    }

    Logger.d("getDaoData: $value value retrieved")

    return value
}

suspend fun <T> getNullableDaoData(dataGetter: suspend () -> T?): T? {
    val value = withContext(Dispatchers.IO) {
        dataGetter()
    }

    Logger.d("getNullableDaoData: $value value retrieved")

    return value
}


fun <T> setDaoAndLiveData(scope: CoroutineScope, value: T, dataSetter: suspend (T) -> Unit, liveDataSetter: (T) -> Unit) {
    scope.launch {
        withContext(Dispatchers.IO) {
            dataSetter(value)
        }

        liveDataSetter(value)

        Logger.d("setDaoAndLiveData: $value value set")
    }
}
