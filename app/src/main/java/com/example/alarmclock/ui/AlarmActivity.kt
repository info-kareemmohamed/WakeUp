package com.example.alarmclock.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmclock.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

            finish()
        }

    }
}