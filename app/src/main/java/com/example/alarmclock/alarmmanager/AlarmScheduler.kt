package com.example.alarmclock.alarmmanager

import com.example.alarmclock.data.entity.Alarm

interface AlarmScheduler {
    fun scheduler(item: Alarm)
    fun cancel(item: Alarm)
}