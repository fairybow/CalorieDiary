package com.fairybow.caloriediary.ui.day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DayViewModel : ViewModel() {
    val budget = MutableLiveData<Int>()

    fun setBudget(budget: Int) {
        viewModelScope.launch {
            this@DayViewModel.budget.value = budget
        }
    }
}
