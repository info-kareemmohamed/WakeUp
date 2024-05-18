package com.example.alarmclock.recyclerview

import com.example.alarmclock.data.entity.Alarm

interface SwitchListener {
    fun onClick(alarm: Alarm,isChecked:Boolean)
}