package com.fairybow.caloriediary.ui.check_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fairybow.caloriediary.CalorieDiary.Companion.repository
import com.fairybow.caloriediary.utilities.ZeroHourDate
import com.fairybow.caloriediary.ui.getNullableWithContext
import com.fairybow.caloriediary.ui.setDaoLiveData
import kotlinx.coroutines.launch

class CheckInViewModel : ViewModel() {
    val lastCheckInDate = MutableLiveData<ZeroHourDate?>()

    init {
        viewModelScope.launch {
            lastCheckInDate.value = getNullableWithContext { repository.getLastCheckInDate() }
        }
    }

    fun setLastCheckInDate(lastCheckInDate: ZeroHourDate) {
        setDaoLiveData(
            scope = viewModelScope,
            value = lastCheckInDate,
            dataSetter = { repository.setLastCheckInDate(it) },
            liveDataSetter = { this.lastCheckInDate.value = lastCheckInDate }
        )
    }
}
