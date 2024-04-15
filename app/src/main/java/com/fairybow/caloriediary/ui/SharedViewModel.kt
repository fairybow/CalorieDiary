package com.fairybow.caloriediary.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fairybow.caloriediary.CalorieDiary.Companion.repository
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.ImperialWeight
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    val activityLevel = MutableLiveData<ActivityLevel>()
    val birthdate = MutableLiveData<ZeroHourDate>()
    val height = MutableLiveData<Double>()
    val imperialWeight = MutableLiveData<ImperialWeight>()
    val kilograms = MutableLiveData<Double>()
    val sex = MutableLiveData<Sex>()
    val useImperialHeight = MutableLiveData<Boolean>()
    val useImperialWeight = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            activityLevel.value = getWithContext { repository.getActivityLevel() }
            birthdate.value = getWithContext { repository.getBirthdate() }
            height.value = getWithContext { repository.getHeight() }
            imperialWeight.value = getWithContext { repository.getImperialWeightType() }
            kilograms.value = getWithContext { repository.getKilograms() }
            sex.value = getWithContext { repository.getSex() }
            useImperialHeight.value = getWithContext { repository.getUseImperialHeight() }
            useImperialWeight.value = getWithContext { repository.getUseImperialWeight() }
        }
    }

    fun setActivityLevel(activityLevel: ActivityLevel) {
        setDaoLiveData(
            scope = viewModelScope,
            value = activityLevel,
            dataSetter = { repository.setActivityLevel(it) },
            liveDataSetter = { this.activityLevel.value = activityLevel }
        )
    }

    fun setBirthdate(birthdate: ZeroHourDate) {
        setDaoLiveData(
            scope = viewModelScope,
            value = birthdate,
            dataSetter = { repository.setBirthdate(it) },
            liveDataSetter = { this.birthdate.value = birthdate }
        )
    }

    fun setHeight(height: Double) {
        setDaoLiveData(
            scope = viewModelScope,
            value = height,
            dataSetter = { repository.setHeight(it) },
            liveDataSetter = { this.height.value = height }
        )
    }

    fun setImperialWeight(imperialWeight: ImperialWeight) {
        setDaoLiveData(
            scope = viewModelScope,
            value = imperialWeight,
            dataSetter = { repository.setImperialWeightType(it) },
            liveDataSetter = { this.imperialWeight.value = imperialWeight }
        )
    }

    fun setKilograms(kilograms: Double) {
        //val rounded = round(kilograms * 100) / 100
        // ^ wacky stuff happens

        setDaoLiveData(
            scope = viewModelScope,
            value = kilograms,
            dataSetter = { repository.setKilograms(it) },
            liveDataSetter = { this.kilograms.value = kilograms }
        )
    }

    fun setSex(sex: Sex) {
        setDaoLiveData(
            scope = viewModelScope,
            value = sex,
            dataSetter = { repository.setSex(it) },
            liveDataSetter = { this.sex.value = sex }
        )
    }

    fun setUseImperialHeight(useImperialHeight: Boolean) {
        setDaoLiveData(
            scope = viewModelScope,
            value = useImperialHeight,
            dataSetter = { repository.setUseImperialHeight(it) },
            liveDataSetter = { this.useImperialHeight.value = useImperialHeight }
        )
    }

    fun setUseImperialWeight(useImperialWeight: Boolean) {
        setDaoLiveData(
            scope = viewModelScope,
            value = useImperialWeight,
            dataSetter = { repository.setUseImperialWeight(it) },
            liveDataSetter = { this.useImperialWeight.value = useImperialWeight }
        )
    }
}
