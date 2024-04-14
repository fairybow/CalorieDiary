package com.fairybow.caloriediary.ui.check_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fairybow.caloriediary.CalorieDiary
import com.fairybow.caloriediary.tools.ZeroHourDate
import com.fairybow.caloriediary.ui.getNullableDaoData
import com.fairybow.caloriediary.ui.setDaoLiveData
import kotlinx.coroutines.launch

class CheckInViewModel : ViewModel() {
    private val journalDao = CalorieDiary.database.journalDao()
    val lastCheckInDate = MutableLiveData<ZeroHourDate?>()

    init {
        viewModelScope.launch {
            lastCheckInDate.value = getNullableDaoData { journalDao.getLastCheckInDate() }
        }
    }

    fun setLastCheckInDate(lastCheckInDate: ZeroHourDate) {
        setDaoLiveData(
            scope = viewModelScope,
            value = lastCheckInDate,
            dataSetter = { journalDao.updateLastCheckInDate(it) },
            liveDataSetter = { this.lastCheckInDate.value = lastCheckInDate }
        )
    }
}
