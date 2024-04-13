package com.fairybow.caloriediary.ui.check_in

import android.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import com.fairybow.caloriediary.R
import com.fairybow.caloriediary.data.ImperialWeight
import com.fairybow.caloriediary.data.ZeroHourDate
import com.fairybow.caloriediary.databinding.FragmentCheckInBinding
import com.fairybow.caloriediary.tools.capitalizeFirst
import com.fairybow.caloriediary.tools.prettify
import com.fairybow.caloriediary.tools.prettyKilograms
import com.fairybow.caloriediary.tools.prettyPounds
import com.fairybow.caloriediary.tools.prettyStones
import com.fairybow.caloriediary.tools.prettyStonesPounds
import com.fairybow.caloriediary.tools.toKilograms
import com.fairybow.caloriediary.tools.toKilogramsFromStones
import com.fairybow.caloriediary.tools.toPounds
import com.fairybow.caloriediary.tools.toStones
import com.fairybow.caloriediary.ui.BaseFragment
import com.fairybow.caloriediary.ui.TextViewBindingHelper

class CheckInFragment : BaseFragment<FragmentCheckInBinding, CheckInViewModel>(
    FragmentCheckInBinding::inflate,
    CheckInViewModel::class.java
) {
    override suspend fun whileOnCreateView() {
        listOf(
            TextViewBindingHelper(
                mutableLiveData = sharedViewModel.kilograms,
                view = binding.currentWeightTextView,
                conversionMethod = { currentWeightText(it) },
                onClick = { showSetWeightDialog(it) }
            ),
            TextViewBindingHelper(
                mutableLiveData = viewModel.lastCheckInDate,
                view = binding.lastCheckInTextView,
                conversionMethod = { lastCheckInDateText(it) },
                onClick = { showSetWeightDialog(sharedViewModel.kilograms.value) }
            )
        ).forEach { it.observe(viewLifecycleOwner) }
    }

    private fun currentWeightText(currentKilograms: Double?): String {
        val kilograms = currentKilograms ?: 0.0 // Can instead handle null condition to show a message

        if (sharedViewModel.useImperialWeight.value == true) {
            return when (sharedViewModel.imperialWeight.value!!) {
                ImperialWeight.POUNDS -> prettyPounds(kilograms)
                ImperialWeight.STONES -> prettyStones(kilograms)
                ImperialWeight.STONES_AND_POUNDS -> prettyStonesPounds(kilograms)
            }
        }

        return prettyKilograms(kilograms)
    }

    private fun lastCheckInDateText(date: ZeroHourDate?): String {
        val label = getString(R.string.check_in_date_label)
        return if (date == null) {
            "$label: ${getString(R.string.check_in_date_null)}"
        } else {
            "$label: ${capitalizeFirst(date.toColloquialString())}"
        }
    }

    // Refactor this nasty boi
    private fun showSetWeightDialog(currentKilograms: Double?) {
        val kilograms = currentKilograms ?: 0.0 // Can instead handle null condition to show a message
        val useImperial = sharedViewModel.useImperialWeight.value
        val imperialType = sharedViewModel.imperialWeight.value!!

        val editText = EditText(requireContext()).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

            val weight = when {
                useImperial == true && imperialType == ImperialWeight.POUNDS -> toPounds(kilograms)
                useImperial == true && imperialType != ImperialWeight.POUNDS -> toStones(kilograms)
                else -> kilograms
            }

            setText(prettify(weight))
        }

        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(editText)
            setPositiveButton(getString(R.string.positive_button)) { _, _ ->
                val newWeight = editText.text.toString().toDouble()
                if (newWeight > 0.0) {
                    val value = when {
                        useImperial == true && imperialType == ImperialWeight.POUNDS -> toKilograms(newWeight)
                        useImperial == true && imperialType != ImperialWeight.POUNDS -> toKilogramsFromStones(newWeight)
                        else -> newWeight
                    }
                    sharedViewModel.setKilograms(value)

                    viewModel.setLastCheckInDate(ZeroHourDate())
                }
            }
        }.create()

        dialog.setOnShowListener {
            editText.requestFocus()
            editText.selectAll()
        }

        dialog.show()
    }
}
