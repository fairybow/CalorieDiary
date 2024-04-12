package com.fairybow.caloriediary.ui.day

import com.fairybow.caloriediary.databinding.FragmentDayBinding
import com.fairybow.caloriediary.tools.getCurrentAge
import com.fairybow.caloriediary.tools.mifflinStJeor
import com.fairybow.caloriediary.ui.BaseFragment
import com.fairybow.caloriediary.ui.TextViewBindingHelper
import kotlin.math.roundToInt

class DayFragment : BaseFragment<FragmentDayBinding, DayViewModel>(
    FragmentDayBinding::inflate,
    DayViewModel::class.java
) {
    // Plus button to add nameless log entry (nameless entries are not saved in Catalog)
    // From Catalog button to add something saved
    override suspend fun whileOnCreateView() {
        listOf(
            TextViewBindingHelper(
                viewModel.budget,
                binding.budgetTextView
            )
        ).forEach { it.observe(viewLifecycleOwner) }

        val liveDataTriggerList = listOf(
            sharedViewModel.activityLevel,
            sharedViewModel.birthdate,
            sharedViewModel.height,
            sharedViewModel.sex,
            sharedViewModel.kilograms
        )

        // TODO: Just make MSJ params nullable and return zero from there?
        liveDataTriggerList.forEach { liveData ->
            liveData.observe(viewLifecycleOwner) {
                if (liveDataTriggerList.any { it.value == null }) {
                    viewModel.setBudget(0)
                } else {
                    viewModel.setBudget(
                        mifflinStJeor(
                            sharedViewModel.kilograms.value!!,
                            sharedViewModel.height.value!!,
                            getCurrentAge(sharedViewModel.birthdate.value!!),
                            sharedViewModel.sex.value!!,
                            sharedViewModel.activityLevel.value!!
                        ).roundToInt()
                    )
                }
            }
        }
    }
}
