package com.fairybow.caloriediary.ui.settings

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import com.fairybow.caloriediary.R
import com.fairybow.caloriediary.data.toSentenceCaseString
import com.fairybow.caloriediary.databinding.FragmentSettingsBinding
import com.fairybow.caloriediary.tools.ZeroHourDate
import com.fairybow.caloriediary.debug.Logger
import com.fairybow.caloriediary.tools.*
import com.fairybow.caloriediary.ui.BaseFragment
import com.fairybow.caloriediary.ui.SwitchBindingHelper
import com.fairybow.caloriediary.ui.TextViewBindingHelper
import kotlin.math.roundToInt

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(
    FragmentSettingsBinding::inflate,
    SettingsViewModel::class.java
) {
    override suspend fun whileOnCreateView() {
        biometricsGroup()
        preferencesGroup()

        addNavFab(
            binding.catalogFab,
            R.id.action_navigation_settings_to_navigation_catalog
        )
    }

    private fun biometricsGroup() {
        listOf(
            TextViewBindingHelper(
                mutableLiveData = sharedViewModel.activityLevel,
                view = binding.activityTextView,
                conversionMethod = { it.toSentenceCaseString() },
                onClick = { showEnumSelectDialog(it, sharedViewModel::setActivityLevel) }
            ),
            TextViewBindingHelper(
                mutableLiveData = sharedViewModel.birthdate,
                view = binding.ageTextView,
                conversionMethod = { getCurrentAge(it).toString() },
                onClick = { showBirthdateSelectDialog(it) }
            ),
            TextViewBindingHelper(
                mutableLiveData = sharedViewModel.height,
                view = binding.heightTextView,
                conversionMethod = { heightTextViewText(it) },
                onClick = { showCorrectHeightDialog(it) }
            ),
            TextViewBindingHelper(
                mutableLiveData = sharedViewModel.sex,
                view = binding.sexTextView,
                conversionMethod = { it.toSentenceCaseString() },
                onClick = { showEnumSelectDialog(it, sharedViewModel::setSex) }
            )
        ).forEach { it.observe(viewLifecycleOwner) }
    }

    private fun preferencesGroup() {
        listOf(
            SwitchBindingHelper(
                mutableLiveData = sharedViewModel.useImperialHeight,
                view = binding.imperialHeightSwitch,
                setter = { sharedViewModel.setUseImperialHeight(it) },
                auxiliaryAction = {
                    binding.heightTextView.text = sharedViewModel.height.value?.let {
                        heightTextViewText(it)
                    }
                }
            ),
            SwitchBindingHelper(
                mutableLiveData = sharedViewModel.useImperialWeight,
                view = binding.imperialWeightSwitch,
                setter = { sharedViewModel.setUseImperialWeight(it) },
                auxiliaryAction = {
                    if (sharedViewModel.useImperialWeight.value == true) {
                        binding.imperialWeightTypeRow.visibility = View.VISIBLE
                    } else {
                        binding.imperialWeightTypeRow.visibility = View.GONE
                    }
                }
            )
        ).forEach { it.observe(viewLifecycleOwner) }

        listOf(
            TextViewBindingHelper(
                mutableLiveData = sharedViewModel.imperialWeight,
                view = binding.imperialWeightTypeTextView,
                conversionMethod = { it.toSentenceCaseString() },
                onClick = { showEnumSelectDialog(it, sharedViewModel::setImperialWeight) }
            )
        ).forEach { it.observe(viewLifecycleOwner) }
    }

    // TODO: Move to Dialog file, and attempt to generalize other similar functions along with this
    private fun <T: Enum<T>> showEnumSelectDialog(currentEnumValue: T, setEnumValue: (T) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        val values = currentEnumValue::class.java.enumConstants.map { it.toSentenceCaseString() }.toTypedArray()
        val checkedItem = currentEnumValue.ordinal

        builder.setSingleChoiceItems(values, checkedItem) { dialog, which ->
            val selectedEnumValue = currentEnumValue::class.java.enumConstants[which]
            setEnumValue(selectedEnumValue)

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
            }, 500)
        }

        builder.create().show()
    }

    private fun showBirthdateSelectDialog(currentBirthdate: ZeroHourDate) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val selectedDate = ZeroHourDate(year, month, day)
                sharedViewModel.setBirthdate(selectedDate)
            },
            currentBirthdate.year(),
            currentBirthdate.month(),
            currentBirthdate.day()
        )

        datePickerDialog.datePicker.minDate = epochYearsAway(-100)
        datePickerDialog.datePicker.maxDate = epochYearsAway(-18)

        datePickerDialog.show()

        Logger.d(toProperCase(currentBirthdate.toColloquialString()))
    }

    private fun heightTextViewText(currentHeight: Double): String {
        return if (sharedViewModel.useImperialHeight.value == true) {
            prettyFeetInches(currentHeight)
        } else {
            prettyCentimeters(currentHeight)
        }
    }

    private fun showCorrectHeightDialog(currentHeight: Double) {
        val range = 54..272

        if (sharedViewModel.useImperialHeight.value == true) {
            showImperialHeightSelectDialog(currentHeight, range)
        } else {
            showMetricHeightSelectDialog(currentHeight, range)
        }
    }

    private fun showMetricHeightSelectDialog(currentHeight: Double, range: IntRange) {
        val centimeterNumberPicker = makeHeightNumberPicker(currentHeight.roundToInt(), range)

        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(centimeterNumberPicker)
            setPositiveButton(getString(R.string.positive_button)) { _, _ ->
                sharedViewModel.setHeight(centimeterNumberPicker.value.toDouble())
            }
        }

        builder.create().show()
    }

    private fun showImperialHeightSelectDialog(currentHeight: Double, range: IntRange) {
        val currentImperialHeight = toFeetInches(currentHeight)
        val feetRange = toFeet(range.first.toDouble()).toInt()..toFeet(range.last.toDouble()).toInt()

        val feetNumberPicker = makeHeightNumberPicker(currentImperialHeight.first, feetRange)
        val inchesNumberPicker = makeHeightNumberPicker(currentImperialHeight.second.roundToInt(), 0..11)

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            addView(feetNumberPicker)
            addView(inchesNumberPicker)
        }

        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(layout)
            setPositiveButton(getString(R.string.positive_button)) { _, _ ->
                val inches = toInchesFromFeet(feetNumberPicker.value.toDouble()) + inchesNumberPicker.value
                sharedViewModel.setHeight(toCentimeters(inches))
            }
        }

        builder.create().show()
    }

    private fun makeHeightNumberPicker(currentValue: Int, minMaxRange: IntRange): NumberPicker {
        return NumberPicker(requireContext()).apply {
            minValue = minMaxRange.first
            maxValue = minMaxRange.last
            value = currentValue
        }
    }
}
