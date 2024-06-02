package com.example.alarmclock.repository

import androidx.lifecycle.LiveData
import com.example.alarmclock.data.alarm.dao.AlarmDao
import com.example.alarmclock.data.alarm.entity.Alarm

class AlarmRepository(private val alarmDao: AlarmDao) {

    suspend fun setAlarm(alarm: Alarm) = alarmDao.setAlarm(alarm)

    suspend fun deleteAlarm(alarm: Alarm) = alarmDao.deleteAlarm(alarm)


    suspend fun updateAlarm(alarm: Alarm) :Int= alarmDao.updateAlarm(alarm)
    suspend fun getLastAlarm(): Alarm? = alarmDao.getLastAlarm()

    fun getAlarm(): LiveData<List<Alarm>> = alarmDao.getAlarm()
    fun getActiveAlarm(): List<Alarm> = alarmDao.getActiveAlarm()
    fun getNotActiveAlarm(): LiveData<List<Alarm>> = alarmDao.getNotActiveAlarm()
    suspend  fun getAlarm(id: Int): Alarm = alarmDao.getAlarm(id)

}