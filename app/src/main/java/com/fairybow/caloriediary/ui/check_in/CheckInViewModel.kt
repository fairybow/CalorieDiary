package com.fairybow.caloriediary.ui.check_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fairybow.caloriediary.CalorieDiary
import com.fairybow.caloriediary.utilities.ZeroHourDate
import com.fairybow.caloriediary.ui.getNullableDaoData
import com.fairybow.caloriediary.ui.setDaoLiveData
import kotlinx.coroutines.launch

class CheckInViewModel : ViewModel() {
    private val dayDao = CalorieDiary.database.dayDao()
    val lastCheckInDate = MutableLiveData<ZeroHourDate?>()

    init {
        viewModelScope.launch {
            lastCheckInDate.value = getNullableDaoData { dayDao.getLastCheckInDate() }
        }
    }

    fun setLastCheckInDate(lastCheckInDate: ZeroHourDate) {
        setDaoLiveData(
            scope = viewModelScope,
            value = lastCheckInDate,
            dataSetter = { dayDao.updateLastCheckInDate(it) },
            liveDataSetter = { this.lastCheckInDate.value = lastCheckInDate }
        )
    }
}
