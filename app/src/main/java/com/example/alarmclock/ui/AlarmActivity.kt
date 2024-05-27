package com.example.alarmclock.ui

import android.app.Activity
import android.content.Intent
import java.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.data.alarm.entity.Alarm
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
        setupTimeNumberPicker()
    }


    private fun setupTimeNumberPicker() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentAmPm = calendar.get(Calendar.AM_PM)

        binding.alarmNumberPickerHour.minValue = 1
        binding.alarmNumberPickerHour.maxValue = 12
        binding.alarmNumberPickerHour.value = if (currentHour == 0) 12 else currentHour

        binding.alarmNumberPickerMinute.minValue = 0
        binding.alarmNumberPickerMinute.maxValue = 59
        binding.alarmNumberPickerMinute.value = currentMinute

        binding.alarmNumberPickerAmPm.minValue = 0
        binding.alarmNumberPickerAmPm.maxValue = 1
        binding.alarmNumberPickerAmPm.displayedValues = arrayOf("AM", "PM")
        binding.alarmNumberPickerAmPm.value = currentAmPm
    }

    private fun setOnClickListenerToBttnSave() {
        binding.alarmButtonSave.setOnClickListener {
            val timePeriod = getTimePeriod(binding.alarmNumberPickerAmPm.value)

            addAlarm(
                getHour(timePeriod, binding.alarmNumberPickerHour.value).toString(),
                binding.alarmNumberPickerMinute.value.toString(),
                timePeriod,
                binding.alarmEditTextMessage.text.toString()
            )


            finish()
        }
    }


    private fun setDataToAlarmScheduler() {
        viewModel.getLastAlarm().observe(this) {
            AndroidAlarmScheduler(context = applicationContext).scheduler(
                it

            )
            Log.d("wwwwwwwwwww", "${it?.hour}  ${it?.minute}  LLLL")


        }


    }

    private fun addAlarm(hour: String, minute: String, timePeriod: String, message: String) {

        val check = viewModel.setAlarm(
            Alarm(
                hour = hour,
                message = message,
                minute = minute,
                active = true,
                timePeriod = timePeriod,
                days = "${Calendar.MONDAY}",
                modeIcon = getIcon(timePeriod),
                id = 0
            )
        )
        Log.d("wwwwwwwww", check.toString())
        if (check != 0L) setDataToAlarmScheduler()
    }


    private fun getHour(timePeriod: String, hour: Int): Int {
        return if (timePeriod == "AM") hour else hour + 12
    }

    private fun getIcon(timePeriod: String): Int {
        return if (timePeriod == "AM") R.drawable.ic_sunny else R.drawable.ic_nightlight
    }

    private fun getTimePeriod(timePeriod: Int): String {
        return if (timePeriod == 0) "AM" else "PM"
    }

}
