package com.example.alarmclock.common.alarmmanager

import com.example.alarmclock.data.model.Alarm

interface AlarmScheduler {
    fun scheduler(item: Alarm?)
    fun cancel(item: Alarm)
}