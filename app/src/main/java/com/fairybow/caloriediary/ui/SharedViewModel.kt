package com.fairybow.caloriediary.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fairybow.caloriediary.CalorieDiary
import com.fairybow.caloriediary.utilities.ActivityLevel
import com.fairybow.caloriediary.utilities.ImperialWeight
import com.fairybow.caloriediary.utilities.Sex
import com.fairybow.caloriediary.utilities.ZeroHourDate
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val database = CalorieDiary.database
    private val biometricsDao = database.biometricsDao()
    private val dayDao = database.dayDao()
    private val preferencesDao = database.preferencesDao()

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
            activityLevel.value = getDaoData { biometricsDao.getActivityLevel() }
            birthdate.value = getDaoData { biometricsDao.getBirthdate() }
            height.value = getDaoData { biometricsDao.getHeight() }
            imperialWeight.value = getDaoData { preferencesDao.getImperialWeightType() }
            kilograms.value = getDaoData { dayDao.getKilograms() }
            sex.value = getDaoData { biometricsDao.getSex() }
            useImperialHeight.value = getDaoData { preferencesDao.getUseImperialHeight() }
            useImperialWeight.value = getDaoData { preferencesDao.getUseImperialWeight() }
        }
    }

    fun setActivityLevel(activityLevel: ActivityLevel) {
        setDaoLiveData(
            scope = viewModelScope,
            value = activityLevel,
            dataSetter = { biometricsDao.updateActivityLevel(it) },
            liveDataSetter = { this.activityLevel.value = activityLevel }
        )
    }

    fun setBirthdate(birthdate: ZeroHourDate) {
        setDaoLiveData(
            scope = viewModelScope,
            value = birthdate,
            dataSetter = { biometricsDao.updateBirthdate(it) },
            liveDataSetter = { this.birthdate.value = birthdate }
        )
    }

    fun setHeight(height: Double) {
        setDaoLiveData(
            scope = viewModelScope,
            value = height,
            dataSetter = { biometricsDao.updateHeight(it) },
            liveDataSetter = { this.height.value = height }
        )
    }

    fun setImperialWeight(imperialWeight: ImperialWeight) {
        setDaoLiveData(
            scope = viewModelScope,
            value = imperialWeight,
            dataSetter = { preferencesDao.updateImperialWeightType(it) },
            liveDataSetter = { this.imperialWeight.value = imperialWeight }
        )
    }

    fun setKilograms(kilograms: Double) {
        //val rounded = round(kilograms * 100) / 100
        // ^ wacky stuff happens

        setDaoLiveData(
            scope = viewModelScope,
            value = kilograms,
            dataSetter = { dayDao.updateKilograms(it) },
            liveDataSetter = { this.kilograms.value = kilograms }
        )
    }

    fun setSex(sex: Sex) {
        setDaoLiveData(
            scope = viewModelScope,
            value = sex,
            dataSetter = { biometricsDao.updateSex(it) },
            liveDataSetter = { this.sex.value = sex }
        )
    }

    fun setUseImperialHeight(useImperialHeight: Boolean) {
        setDaoLiveData(
            scope = viewModelScope,
            value = useImperialHeight,
            dataSetter = { preferencesDao.updateUseImperialHeight(it) },
            liveDataSetter = { this.useImperialHeight.value = useImperialHeight }
        )
    }

    fun setUseImperialWeight(useImperialWeight: Boolean) {
        setDaoLiveData(
            scope = viewModelScope,
            value = useImperialWeight,
            dataSetter = { preferencesDao.updateUseImperialWeight(it) },
            liveDataSetter = { this.useImperialWeight.value = useImperialWeight }
        )
    }
}
