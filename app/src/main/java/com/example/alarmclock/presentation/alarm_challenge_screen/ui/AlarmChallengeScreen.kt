package com.example.alarmclock.presentation.alarm_challenge_screen.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.alarmclock.R
import com.example.alarmclock.common.Notification
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.databinding.ActivityAlarmChallengeBinding
import com.example.alarmclock.presentation.alarm_challenge_screen.view_model.AlarmChallengeViewModel
import com.example.alarmclock.service.AlarmsService
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AlarmChallengeScreen : AppCompatActivity(), OnClickListener {
    private var alarm: Alarm? = null
    private lateinit var binding: ActivityAlarmChallengeBinding
    private val viewModel: AlarmChallengeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_challenge)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setClickListener()
        viewModel.alarm.observe(this) { alarm = it }

    }


    private fun setClickListener() {
        binding.apply {
            AlarmNotificationAnswer1.setOnClickListener(this@AlarmChallengeScreen)
            AlarmNotificationAnswer2.setOnClickListener(this@AlarmChallengeScreen)
            AlarmNotificationAnswer3.setOnClickListener(this@AlarmChallengeScreen)
            AlarmNotificationSnooze.setOnClickListener(this@AlarmChallengeScreen)
        }
    }

    private fun cancelAlarm() {
        alarm?.apply {
            getDaysOfWeek().toMutableList().let { days ->
                if (days.size > 1) days.remove(
                    Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                ) else active = false
                setDaysOfWeek(days)
            }
            viewModel.updateAlarm(this)
            Notification.cancelNotification(this@AlarmChallengeScreen, id)
            stopService(Intent(this@AlarmChallengeScreen, AlarmsService::class.java))
            finishAndRemoveTask() // Remove the app from the recent apps list

        }
    }

    override fun onPause() {
        super.onPause()
        finishAndRemoveTask()// Remove the app from the recent apps list
    }

    private fun handleSnoozeButton() {
        alarm?.apply {
            viewModel.snoozeAlarm()
            Notification.cancelNotification(this@AlarmChallengeScreen, id)
            stopService(Intent(this@AlarmChallengeScreen, AlarmsService::class.java))
            finishAndRemoveTask()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.AlarmNotification_snooze) {
            handleSnoozeButton()
            return
        }
        val answer = when (v?.id) {
            R.id.AlarmNotification_answer1 -> 0
            R.id.AlarmNotification_answer2 -> 1
            R.id.AlarmNotification_answer3 -> 2
            else -> return
        }

        if (answer == viewModel.question.value?.correctAnswerIndex) {
            cancelAlarm()
        } else {
            Toast.makeText(this, R.string.wrong_answer, Toast.LENGTH_SHORT).show()
        }
    }


}