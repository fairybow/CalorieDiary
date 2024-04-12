package com.fairybow.caloriediary.ui.check_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fairybow.caloriediary.CalorieDiary
import com.fairybow.caloriediary.data.ZeroHourDate
import com.fairybow.caloriediary.ui.getNullableLiveData
import com.fairybow.caloriediary.ui.setLiveData
import kotlinx.coroutines.launch

class CheckInViewModel : ViewModel() {
    private val journalDao = CalorieDiary.database.journalDao()
    val lastCheckInDate = MutableLiveData<ZeroHourDate?>()

    init {
        viewModelScope.launch {
            lastCheckInDate.value = getNullableLiveData { journalDao.getLastCheckInDate() }
        }
    }

    fun setLastCheckInDate(lastCheckInDate: ZeroHourDate) {
        setLiveData(
            viewModelScope,
            lastCheckInDate,
            { journalDao.updateLastCheckInDate(it) },
            { this.lastCheckInDate.value = lastCheckInDate }
        )
    }
}
