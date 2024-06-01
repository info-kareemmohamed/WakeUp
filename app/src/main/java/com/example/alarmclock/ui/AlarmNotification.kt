package com.example.alarmclock.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.core.Notification
import com.example.alarmclock.data.question.Question
import com.example.alarmclock.databinding.ActivityAlarmNotificationBinding
import com.example.alarmclock.repository.QuestionRepository
import com.example.alarmclock.viewmodel.AlarmViewModel


class AlarmNotification : AppCompatActivity(), OnClickListener {
    private lateinit var intent: Intent
    private var time: String = ""
    private var message: String = ""
    private var notificationId = 1
    private lateinit var binding: ActivityAlarmNotificationBinding
    private lateinit var question: Question
    private  lateinit var viewModel: AlarmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this)[AlarmViewModel::class.java]
        getDataFromIntent()
        setClickListener()
        setQuestion()


    }

    private fun getDataFromIntent() {
        intent = getIntent()
        time = intent.getStringExtra("time")!!
        message = intent.getStringExtra("message") ?: ""
        notificationId = intent.getIntExtra("alarmId", 1)
        binding.AlarmNotificationTime.text = time
        binding.AlarmNotificationMessage.text = message
    }

    private fun setClickListener() {
        binding.AlarmNotificationAnswer1.setOnClickListener(this)
        binding.AlarmNotificationAnswer2.setOnClickListener(this)
        binding.AlarmNotificationAnswer3.setOnClickListener(this)

    }

    private fun setQuestion() {
        question = QuestionRepository().getQuestion()
        binding.AlarmNotificationQuestion.text = question.questionText
        binding.AlarmNotificationAnswer1.text = question.answerOptions[0]
        binding.AlarmNotificationAnswer2.text = question.answerOptions[1]
        binding.AlarmNotificationAnswer3.text = question.answerOptions[2]
    }

    private fun cancelAlarm(){


        Notification.cancelNotification(this@AlarmNotification, notificationId)
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.AlarmNotification_answer1, R.id.AlarmNotification_answer2, R.id.AlarmNotification_answer3->{
                val answer = (v.tag).toString().toInt()
                if (answer == question.correctAnswerIndex) {
                    cancelAlarm()
                } else
                    Toast.makeText(this@AlarmNotification,R.string.wrong_answer,Toast.LENGTH_SHORT).show()
            }

        }


    }


}