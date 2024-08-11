package com.example.alarmclock.presentation.home_screen.recyclerview

import com.example.alarmclock.data.model.Alarm

interface SwitchListener {
    fun onClick(alarm: Alarm, isChecked:Boolean)
}