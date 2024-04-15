package com.fairybow.caloriediary.ui.day

import com.fairybow.caloriediary.databinding.FragmentDayBinding
import com.fairybow.caloriediary.utilities.UNIT_RATE_KC
import com.fairybow.caloriediary.utilities.getCurrentAge
import com.fairybow.caloriediary.utilities.mifflinStJeor
import com.fairybow.caloriediary.utilities.prettify
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
                mutableLiveData = viewModel.budget,
                view = binding.currentBudgetTextView,
                conversionMethod = { prettify(it) }
            )
        ).forEach { it.observe(viewLifecycleOwner) }

        binding.budgetUnitRateTextView.text = UNIT_RATE_KC

        listOf(
            sharedViewModel.activityLevel,
            sharedViewModel.birthdate,
            sharedViewModel.height,
            sharedViewModel.sex,
            sharedViewModel.kilograms
        ).forEach { liveData ->
            liveData.observe(viewLifecycleOwner) {
                viewModel.setBudget(
                    mifflinStJeor(
                        sharedViewModel.kilograms.value,
                        sharedViewModel.height.value,
                        sharedViewModel.birthdate.value?.let { it1 -> getCurrentAge(it1) },
                        sharedViewModel.sex.value,
                        sharedViewModel.activityLevel.value
                    ).roundToInt()
                )
            }
        }

        addNavFab(
            fab = binding.addFab,
            action = {
                /* TODO: show dialog with name entry and value entry options;
                    when no name is entered, do not save to catalog, but add to Day;
                    when name is entered, save to catalog and then also add a copy to Day */
            }
        )
    }
}
