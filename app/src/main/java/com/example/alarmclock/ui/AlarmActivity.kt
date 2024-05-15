package com.example.alarmclock.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.data.entity.Alarm
import com.example.alarmclock.databinding.ActivityAlarmBinding
import com.example.alarmclock.viewmodel.AlarmViewModel

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var viewModel: AlarmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        setOnClickListenerToBttnSave()

    }


    private fun setOnClickListenerToBttnSave() {
        binding.AlarmAppCompatButtonSave.setOnClickListener {
            val hour: Int
            val minute: Int

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = binding.AlarmTimePicker.hour
                minute = binding.AlarmTimePicker.minute

            } else {
                hour = binding.AlarmTimePicker.currentHour
                minute = binding.AlarmTimePicker.currentMinute
            }

            addAlarm(hour, minute)
            finish()
        }

    }

    private fun addAlarm(hour: Int, minute: Int) {

        viewModel.setAlarm(
            Alarm(

                hour = convertTo12HourFormat(hour),
                message = "dd",
                minute = minute.toString(),
                active = true,
                abbreviations = getTimeSuffix(hour),
                days = "Tu",
                modeIcon = getIcon(hour),
                id = 0
            )
        )
    }

    private fun convertTo12HourFormat(hourOfDay: Int): String {
        return if (hourOfDay > 12) (hourOfDay - 12).toString() else hourOfDay.toString()
    }

    private fun getTimeSuffix(hourOfDay: Int): String {
        return if (hourOfDay < 12) "AM" else "PM"
    }

    private fun getIcon(hourOfDay: Int): Int {
        return if (hourOfDay < 12) R.drawable.ic_sunny else R.drawable.ic_nightlight
    }
}