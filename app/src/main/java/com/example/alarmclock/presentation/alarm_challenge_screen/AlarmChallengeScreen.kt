package com.example.alarmclock.presentation.alarm_challenge_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import com.example.alarmclock.R
import com.example.alarmclock.common.core.Constant
import com.example.alarmclock.common.core.Notification
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.data.model.Question
import com.example.alarmclock.databinding.ActivityAlarmChallengeBinding
import com.example.alarmclock.service.AlarmsService
import com.example.alarmclock.presentation.viewmodel.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmChallengeScreen : AppCompatActivity(), OnClickListener {
    private var alarm: Alarm? = null
    private var time: String = ""
    private var id: Int = 1
    private lateinit var binding: ActivityAlarmChallengeBinding
    private lateinit var question: Question
    private val viewModel: AlarmViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromIntent()
        setClickListener()
        setQuestion()


    }

    private fun getDataFromIntent() {
        time = intent.getStringExtra(Constant.EXTRA_TIME)!!
        id = intent.getIntExtra(Constant.EXTRA_ID, 1)
        alarm = viewModel.getAlarmById(id)
        binding.AlarmNotificationTime.text = time
        binding.AlarmNotificationMessage.text = alarm?.message
    }

    private fun setClickListener() {
        binding.AlarmNotificationAnswer1.setOnClickListener(this)
        binding.AlarmNotificationAnswer2.setOnClickListener(this)
        binding.AlarmNotificationAnswer3.setOnClickListener(this)

    }

    private fun setQuestion() {
        question = viewModel.getQuestion()
        binding.AlarmNotificationQuestion.text = question.questionText
        binding.AlarmNotificationAnswer1.text = question.answerOptions[0]
        binding.AlarmNotificationAnswer2.text = question.answerOptions[1]
        binding.AlarmNotificationAnswer3.text = question.answerOptions[2]
    }

    private fun cancelAlarm() {
        val days = alarm?.getDaysOfWeek()?.toMutableList()
        if (days != null && days.size > 1) {
            days.removeAt(0)
            alarm?.setDaysOfWeek(days)
        } else {
            alarm?.active = false
        }
        alarm?.let {
            viewModel.updateAlarm(it)

            Notification.cancelNotification(this@AlarmChallengeScreen, it.id)
            stopService(Intent(this, AlarmsService::class.java))
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.AlarmNotification_answer1, R.id.AlarmNotification_answer2, R.id.AlarmNotification_answer3 -> {
                val answer = (v.tag).toString().toInt()
                if (answer == question.correctAnswerIndex) {
                    cancelAlarm()
                } else
                    Toast.makeText(
                        this@AlarmChallengeScreen,
                        R.string.wrong_answer,
                        Toast.LENGTH_SHORT
                    ).show()
            }

        }


    }


}