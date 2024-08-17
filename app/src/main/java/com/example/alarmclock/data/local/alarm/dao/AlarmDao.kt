package com.example.alarmclock.data.local.alarm.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alarmclock.data.model.Alarm


@Dao
interface AlarmDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm):Long

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm):Int
    @Query("SELECT * FROM alarm_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastAlarm(): Alarm?
    @Query("select * from alarm_table")
    fun getAlarm(): LiveData<List<Alarm>>

    @Query("select * from alarm_table where active= 1")
    fun getActiveAlarm(): List<Alarm>

    @Query("select * from alarm_table where active= 0")
    fun getNotActiveAlarm(): LiveData<List<Alarm>>

    @Query("SELECT * FROM alarm_table WHERE id = :id")
    suspend fun getAlarmById(id: Int): Alarm
}