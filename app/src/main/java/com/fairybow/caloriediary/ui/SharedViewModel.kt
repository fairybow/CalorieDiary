package com.fairybow.caloriediary.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fairybow.caloriediary.CalorieDiary
import com.fairybow.caloriediary.data.ActivityLevel
import com.fairybow.caloriediary.data.Sex
import com.fairybow.caloriediary.data.ZeroHourDate
import com.fairybow.caloriediary.tools.ImperialWeight
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val biometricsDao = CalorieDiary.database.biometricsDao()
    val activityLevel = MutableLiveData<ActivityLevel>()
    val birthdate = MutableLiveData<ZeroHourDate>()
    val height = MutableLiveData<Double>()
    val sex = MutableLiveData<Sex>()

    private val journalDao = CalorieDiary.database.journalDao()
    val kilograms = MutableLiveData<Double>()

    private val preferencesDao = CalorieDiary.database.preferencesDao()
    val imperialWeight = MutableLiveData<ImperialWeight>()
    val useImperialHeight = MutableLiveData<Boolean>()
    val useImperialWeight = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            activityLevel.value = getLiveData { biometricsDao.getActivityLevel() }
            birthdate.value = getLiveData { biometricsDao.getBirthdate() }
            height.value = getLiveData { biometricsDao.getHeight() }
            sex.value = getLiveData { biometricsDao.getSex() }

            kilograms.value = getLiveData { journalDao.getKilograms() }

            imperialWeight.value = getLiveData { preferencesDao.getImperialWeight() }
            useImperialHeight.value = getLiveData { preferencesDao.getUseImperialHeight() }
            useImperialWeight.value = getLiveData { preferencesDao.getUseImperialWeight() }
        }
    }

    fun setActivityLevel(activityLevel: ActivityLevel) {
        setLiveData(
            viewModelScope,
            activityLevel,
            { biometricsDao.updateActivityLevel(it) },
            { this.activityLevel.value = activityLevel }
        )
    }

    fun setBirthdate(birthdate: ZeroHourDate) {
        setLiveData(
            viewModelScope,
            birthdate,
            { biometricsDao.updateBirthdate(it) },
            { this.birthdate.value = birthdate }
        )
    }

    fun setHeight(height: Double) {
        setLiveData(
            viewModelScope,
            height,
            { biometricsDao.updateHeight(it) },
            { this.height.value = height }
        )
    }

    fun setSex(sex: Sex) {
        setLiveData(
            viewModelScope,
            sex,
            { biometricsDao.updateSex(it) },
            { this.sex.value = sex }
        )
    }

    fun setKilograms(kilograms: Double) {
        //val rounded = round(kilograms * 100) / 100
        // ^ wacky stuff happens

        setLiveData(
            viewModelScope,
            kilograms,
            { journalDao.updateKilograms(it) },
            { this.kilograms.value = kilograms }
        )
    }

    fun setImperialWeight(imperialWeight: ImperialWeight) {
        setLiveData(
            viewModelScope,
            imperialWeight,
            { preferencesDao.updateImperialWeight(it) },
            { this.imperialWeight.value = imperialWeight }
        )
    }

    fun setUseImperialHeight(useImperialHeight: Boolean) {
        setLiveData(
            viewModelScope,
            useImperialHeight,
            { preferencesDao.updateUseImperialHeight(it) },
            { this.useImperialHeight.value = useImperialHeight }
        )
    }

    fun setUseImperialWeight(useImperialWeight: Boolean) {
        setLiveData(
            viewModelScope,
            useImperialWeight,
            { preferencesDao.updateUseImperialWeight(it) },
            { this.useImperialWeight.value = useImperialWeight }
        )
    }
}
