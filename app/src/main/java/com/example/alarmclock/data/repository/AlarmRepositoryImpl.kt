package com.example.alarmclock.data.repository

import androidx.lifecycle.LiveData
import com.example.alarmclock.data.local.alarm.dao.AlarmDao
import com.example.alarmclock.data.local.question.QuestionData
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.data.model.Question
import com.example.alarmclock.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val questionData: QuestionData
) : AlarmRepository {
    override suspend fun insertAlarm(alarm: Alarm): Long = alarmDao.insertAlarm(alarm)

    override suspend fun deleteAlarm(alarm: Alarm) = alarmDao.deleteAlarm(alarm)

    override suspend fun updateAlarm(alarm: Alarm): Int = alarmDao.updateAlarm(alarm)

    override suspend fun getLastAlarm(): Alarm? = alarmDao.getLastAlarm()

    override fun getAlarm(): LiveData<List<Alarm>> = alarmDao.getAlarm()

    override suspend fun getAlarmById(id: Int): Alarm = alarmDao.getAlarmById(id)

    override fun getQuestion(): Question = questionData.getQuestion()

    override fun getActiveAlarm(): List<Alarm> = alarmDao.getActiveAlarm()

    override fun getNotActiveAlarm(): LiveData<List<Alarm>> = alarmDao.getNotActiveAlarm()
}