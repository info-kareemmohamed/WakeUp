package com.example.alarmclock.data

import androidx.lifecycle.LiveData
import com.example.alarmclock.data.dao.AlarmDao
import com.example.alarmclock.data.entity.Alarm

class AlarmRepository(private val alarmDao: AlarmDao) {

    suspend fun setAlarm(alarm: Alarm) = alarmDao.setAlarm(alarm)
    fun getAlarm(): LiveData<List<Alarm>> = alarmDao.getAlarm()
     fun getActiveAlarm(): LiveData<List<Alarm>> = alarmDao.getActiveAlarm()
    fun getNotActiveAlarm(): LiveData<List<Alarm>> = alarmDao.getNotActiveAlarm()
     fun getAlarm(id: Int): Alarm = alarmDao.getAlarm(id)

}