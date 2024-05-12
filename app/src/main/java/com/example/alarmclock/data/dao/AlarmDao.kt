package com.example.alarmclock.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.alarmclock.data.entity.Alarm

@Dao
interface AlarmDao {
    @Insert
    fun setAlarm(alarm: Alarm)

    @Query("select * from alarm_table")
    fun getAlarm(): List<Alarm>

    @Query("select * from alarm_table where active= 1")
    fun getActiveAlarm(): List<Alarm>

    @Query("select * from alarm_table where active= 0")
    fun getNotActiveAlarm(): List<Alarm>

    @Query("SELECT * FROM alarm_table WHERE id = :id")
    fun getAlarm(id: Int): Alarm
}