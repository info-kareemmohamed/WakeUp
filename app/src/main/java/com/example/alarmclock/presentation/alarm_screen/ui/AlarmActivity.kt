package com.example.alarmclock.presentation.alarm_screen.ui

import java.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.alarmclock.R
import com.example.alarmclock.common.dayNameToCalendar
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.databinding.ActivityAlarmBinding
import com.example.alarmclock.presentation.alarm_screen.view_model.AlarmViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AlarmActivity : AppCompatActivity(), View.OnClickListener,
    DayBottomSheet.OnDaysSaveClickListener, SoundBottomSheet.OnSoundSaveClickListener {
    private var alarm: Alarm? = null
    private var sound: Int = R.raw.default_sound
    private lateinit var binding: ActivityAlarmBinding
    private val viewModel: AlarmViewModel by viewModels()
    private var dayOfWeek: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.alarm.observe(this) {
            alarm = it
            if (alarm != null) dayOfWeek = alarm!!.days
        }
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.alarmTextViewDays.setOnClickListener(this)
        binding.alarmTextViewTitleDays.setOnClickListener(this)
        binding.alarmTextViewSound.setOnClickListener(this)
        binding.alarmTextViewTitleSound.setOnClickListener(this)
        binding.alarmButtonSave.setOnClickListener(this)
    }


    private fun handleSaveButtonClick() {

        val timePeriod = if (binding.alarmNumberPickerAmPm.value == 0) "AM" else "PM"
        val hour = getHour(timePeriod, binding.alarmNumberPickerHour.value)

        schedulerAlarm(
            hour.toString(),
            binding.alarmNumberPickerMinute.value.toString(),
            timePeriod,
            binding.alarmEditTextMessage.text.toString()
        )
        finish()

    }



    private fun getDaysOfWeek(): String {
        if (dayOfWeek.isEmpty()) {
            val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            return currentDayOfWeek.toString()
        }
        return dayOfWeek
    }

    private fun schedulerAlarm(
        hour: String,
        minute: String,
        timePeriod: String,
        message: String
    ) {
        alarm = Alarm(
            id = alarm?.id ?: 0,
            hour = hour.toInt(),
            minute = minute.toInt(),
            message = message,
            timePeriod = timePeriod,
            days = getDaysOfWeek(),
            sound = sound,
            active = true
        )
        val result = viewModel.insertAlarm(alarm!!)
        if (result.toInt() != 0) viewModel.getLastAlarm().observe(this) { viewModel.schedulerAlarm(it!!) }
    }


    private fun getHour(timePeriod: String, hour: Int): Int {
        return if (timePeriod == "PM" && hour != 12) hour + 12
        else if (timePeriod == "AM" && hour == 12) 0
        else hour
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.alarm_text_view_title_days, R.id.alarm_text_view_days -> showBottomSheet(DayBottomSheet(), DayBottomSheet.TAG)
            R.id.alarm_text_view_title_sound, R.id.alarm_text_view_sound -> showBottomSheet(SoundBottomSheet(), SoundBottomSheet.TAG)
            R.id.alarm_button_save -> handleSaveButtonClick()
        }
    }

    private fun showBottomSheet(modal: BottomSheetDialogFragment, tag: String) {
        supportFragmentManager.let { modal.show(it, tag) }
    }


    override fun onButtonSave(days: List<String>) {
        binding.alarmTextViewDays.text = days.joinToString(",")
        dayOfWeek = days.joinToString(",") { dayNameToCalendar(it) }
    }


    override fun onButtonSave(sound: Int, name: String) {
        binding.alarmTextViewSound.text = name
        this.sound = sound
    }


}