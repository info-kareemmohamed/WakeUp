package com.example.alarmclock.presentation.binding_adapter

import android.widget.NumberPicker
import androidx.databinding.BindingAdapter

@BindingAdapter("minValue")
 fun setMinValue(numberPicker: NumberPicker, minValue: Int) {
    numberPicker.minValue = minValue
}

@BindingAdapter("maxValue")
 fun setMaxValue(numberPicker: NumberPicker, maxValue: Int) {
    numberPicker.maxValue = maxValue
}

@BindingAdapter("value")
 fun setValue(numberPicker: NumberPicker, value: Int) {
    numberPicker.value = value
}

@BindingAdapter("displayedValuesAmAndPm")
 fun setDisplayedValues(numberPicker: NumberPicker, displayedValues: Boolean) {
    if (displayedValues) {
        numberPicker.displayedValues = arrayOf("AM", "PM")
    }
}
