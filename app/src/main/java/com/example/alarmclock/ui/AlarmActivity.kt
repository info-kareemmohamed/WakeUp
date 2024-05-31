package com.example.alarmclock.ui

import android.app.Activity
import android.content.Intent
import java.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.data.alarm.entity.Alarm
import com.example.alarmclock.databinding.ActivityAlarmBinding
import com.example.alarmclock.viewmodel.AlarmViewModel

class AlarmActivity : AppCompatActivity(), View.OnClickListener,
    BottomSheet.OnDaysSaveClickListener {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var viewModel: AlarmViewModel
    private var dayOfWeek: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        setOnClickListener()
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

    private fun setOnClickListener() {
        binding.alarmTextViewDays.setOnClickListener(this)
        binding.alarmTextViewTitleDays.setOnClickListener(this)
        binding.alarmTextViewSound.setOnClickListener(this)
        binding.alarmTextViewTitleSound.setOnClickListener(this)
        binding.alarmButtonSave.setOnClickListener(this)
    }


    private fun handleSaveButtonClick() {

        val timePeriod = getTimePeriod(binding.alarmNumberPickerAmPm.value)
        val hour = getHour(timePeriod, binding.alarmNumberPickerHour.value)

        addAlarm(
            hour.toString(),
            binding.alarmNumberPickerMinute.value.toString(),
            timePeriod,
            binding.alarmEditTextMessage.text.toString()
        )


        finish()

    }


    private fun setDataToAlarmScheduler() {
        viewModel.getLastAlarm().observe(this) {
            AndroidAlarmScheduler(context = applicationContext).scheduler(
                it
            )
        }
    }

    private fun getDaysOfWeek(): String {
        if (dayOfWeek.isEmpty()) {
            val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            return currentDayOfWeek.toString()
        }
        return dayOfWeek
    }

    private fun addAlarm(hour: String, minute: String, timePeriod: String, message: String) {

        val check = viewModel.setAlarm(
            Alarm(
                hour = hour,
                message = message,
                minute = minute,
                active = true,
                timePeriod = timePeriod,
                days = getDaysOfWeek(),
                modeIcon = getIcon(timePeriod),
                id = 0
            )
        )
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.alarm_text_view_title_days, R.id.alarm_text_view_days -> showBottomSheet()
            R.id.alarm_text_view_title_sound, R.id.alarm_text_view_sound -> Toast.makeText(
                this@AlarmActivity,
                "Set sound",
                Toast.LENGTH_LONG
            ).show()

            R.id.alarm_button_save -> handleSaveButtonClick()
        }
    }

    private fun showBottomSheet() {
        val modal = BottomSheet()
        supportFragmentManager.let { modal.show(it, BottomSheet.TAG) }
    }

    override fun onButtonSave(days: List<String>) {
        binding.alarmTextViewDays.text = days.joinToString(",")
        dayOfWeek = days.joinToString(",") { calendarDay(it) }
    }

    private fun calendarDay(day: String): String {
        return when (day) {
            "Sun" -> "${Calendar.SUNDAY}"
            "Mon" -> "${Calendar.MONDAY}"
            "Tue" -> "${Calendar.TUESDAY}"
            "Wed" -> "${Calendar.WEDNESDAY}"
            "Thu" -> "${Calendar.THURSDAY}"
            "Fri" -> "${Calendar.FRIDAY}"
            "Sat" -> "${Calendar.SATURDAY}"
            else -> ""
        }

    }


}