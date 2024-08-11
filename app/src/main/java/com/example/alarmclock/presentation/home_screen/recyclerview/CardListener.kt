package com.example.alarmclock.presentation.home_screen.recyclerview

import com.example.alarmclock.data.model.Alarm

interface CardListener {

    fun onClick(alarm: Alarm)

}