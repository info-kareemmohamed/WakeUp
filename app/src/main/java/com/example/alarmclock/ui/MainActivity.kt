package com.example.alarmclock.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.databinding.ActivityMainBinding
import com.example.alarmclock.entity.Alarm
import com.example.alarmclock.recyclerview.RecyclerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.MainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MainRecyclerView.setHasFixedSize(true)
        val alarms = arrayListOf(
            Alarm("Monday", "08:00", true, R.drawable.ic_nightlight, "AM"),
            Alarm("Tuesday", "09:00", false, R.drawable.ic_sunny, "PM"),
        )
        binding.MainRecyclerView.adapter = RecyclerAdapter(alarms)
        binding.MainBottomNavigation.background=null

    }
}