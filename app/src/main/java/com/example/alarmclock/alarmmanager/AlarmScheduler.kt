package com.example.alarmclock.alarmmanager

import com.example.alarmclock.entity.Alarm

interface AlarmScheduler {
    fun scheduler(item: Alarm)
    fun cancel(item: Alarm)
}