package com.example.alarmclock.ui

import android.app.Activity
import android.content.Intent
import java.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.core.Constant
import com.example.alarmclock.data.alarm.entity.Alarm
import com.example.alarmclock.databinding.ActivityAlarmBinding
import com.example.alarmclock.service.AlarmsService
import com.example.alarmclock.viewmodel.AlarmViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.log

class AlarmActivity : AppCompatActivity(), View.OnClickListener,
    DayBottomSheet.OnDaysSaveClickListener, SoundBottomSheet.OnSoundSaveClickListener {
    private lateinit var intent: Intent
    private var alarm: Alarm? = null
    private var sound: Int = R.raw.default_sound
    private var oldAlarm: Alarm? = null
    private var updateMode = false
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var viewModel: AlarmViewModel
    private var dayOfWeek: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        setOnClickListener()
        getDataFromIntent()
    }


    private fun getDataFromIntent() {
        intent = getIntent()
        alarm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_ALARM, Alarm::class.java)
        } else {
            intent.getParcelableExtra(Constant.EXTRA_ALARM)
        }
        oldAlarm = alarm
        if (alarm != null) {
            updateMode = true
            setupTimeNumberPicker(
                alarm!!.hour.toInt() % 12,
                alarm!!.minute.toInt(),
                if (alarm!!.active.equals("AM")) Calendar.AM else Calendar.PM
            )
            binding.alarmTextViewDays.text =
                alarm?.getDaysOfWeek()?.joinToString(",") { dayName(it) }
            dayOfWeek = alarm?.days + ""
            binding.alarmEditTextMessage.text =
                Editable.Factory.getInstance().newEditable(alarm?.message)
        } else {
            setupDefaultTimeNumberPicker()
        }

    }


    private fun setupDefaultTimeNumberPicker() {
        val calendar = Calendar.getInstance()
        setupTimeNumberPicker(
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.AM_PM)
        )
    }

    private fun setupTimeNumberPicker(hour: Int, minute: Int, am_pm: Int) {


        binding.alarmNumberPickerHour.minValue = 1
        binding.alarmNumberPickerHour.maxValue = 12
        binding.alarmNumberPickerHour.value = if (hour == 0) 12 else hour

        binding.alarmNumberPickerMinute.minValue = 0
        binding.alarmNumberPickerMinute.maxValue = 59
        binding.alarmNumberPickerMinute.value = minute

        binding.alarmNumberPickerAmPm.minValue = 0
        binding.alarmNumberPickerAmPm.maxValue = 1
        binding.alarmNumberPickerAmPm.displayedValues = arrayOf("AM", "PM")
        binding.alarmNumberPickerAmPm.value = am_pm
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

        addOrUpdateAlarm(
            hour.toString(),
            binding.alarmNumberPickerMinute.value.toString(),
            timePeriod,
            binding.alarmEditTextMessage.text.toString()
        )
        finish()

    }


    private fun setDataToAlarmScheduler() {
        viewModel.getLastAlarm().observe(this) {
            AndroidAlarmScheduler(context = applicationContext).scheduler(it)
        }
    }

    private fun getDaysOfWeek(): String {
        if (dayOfWeek.isEmpty()) {
            val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            return currentDayOfWeek.toString()
        }
        return dayOfWeek
    }

    private fun addOrUpdateAlarm(
        hour: String,
        minute: String,
        timePeriod: String,
        message: String
    ) {
        alarm = Alarm(
            id = alarm?.id ?: 0,
            hour = hour,
            minute = minute,
            message = message,
            timePeriod = timePeriod,
            modeIcon = getIcon(timePeriod),
            days = getDaysOfWeek(),
            sound = sound,
            active = true
        )

        val result = if (updateMode) {
            AndroidAlarmScheduler(this).cancel(oldAlarm!!)
            viewModel.updateAlarm(alarm!!)
        } else {
            viewModel.setAlarm(alarm!!)
        }
        if (result.toInt() != 0) setDataToAlarmScheduler()
    }


    private fun getHour(timePeriod: String, hour: Int): Int {
        return if (timePeriod == "PM" && hour != 12) {
            hour + 12
        } else if (timePeriod == "AM" && hour == 12) {
            0
        } else {
            hour
        }
    }

    private fun getIcon(timePeriod: String): Int {
        return if (timePeriod == "AM") R.drawable.ic_sunny else R.drawable.ic_nightlight
    }

    private fun getTimePeriod(timePeriod: Int): String {
        return if (timePeriod == 0) "AM" else "PM"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.alarm_text_view_title_days, R.id.alarm_text_view_days -> showBottomSheet(
                DayBottomSheet(),
                DayBottomSheet.TAG
            )

            R.id.alarm_text_view_title_sound, R.id.alarm_text_view_sound -> showBottomSheet(
                SoundBottomSheet(),
                SoundBottomSheet.TAG
            )

            R.id.alarm_button_save -> handleSaveButtonClick()
        }
    }

    private fun showBottomSheet(modal: BottomSheetDialogFragment, tag: String) {
        supportFragmentManager.let { modal.show(it, DayBottomSheet.TAG) }
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

    private fun dayName(day: Int): String {
        return when (day) {
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            else -> ""
        }
    }

    override fun onButtonSave(sound: Int, name: String) {
        binding.alarmTextViewSound.text = name
        this.sound = sound
    }

}