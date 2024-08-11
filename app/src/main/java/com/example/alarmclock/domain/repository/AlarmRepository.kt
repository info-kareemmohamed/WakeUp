package com.example.alarmclock.domain.repository

import androidx.lifecycle.LiveData
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.data.model.Question

interface AlarmRepository {
    suspend fun insertAlarm(alarm: Alarm): Long
    suspend fun deleteAlarm(alarm: Alarm)
    suspend fun updateAlarm(alarm: Alarm): Int
    suspend fun getLastAlarm(): Alarm?
    fun getAlarm(): LiveData<List<Alarm>>
    fun getActiveAlarm(): List<Alarm>
    fun getNotActiveAlarm(): LiveData<List<Alarm>>
    suspend fun getAlarmById(id: Int): Alarm
    fun getQuestion(): Question

}