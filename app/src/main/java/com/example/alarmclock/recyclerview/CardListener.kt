package com.example.alarmclock.recyclerview

import com.example.alarmclock.data.alarm.entity.Alarm

interface CardListener {

    fun onClick(alarm: Alarm)

}