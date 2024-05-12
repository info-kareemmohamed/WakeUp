package com.example.alarmclock.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.alarmclock.data.entity.Alarm
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmDao {
    @Insert
    suspend fun setAlarm(alarm: Alarm)

    @Query("select * from alarm_table")
    suspend fun getAlarm(): List<Alarm>

    @Query("select * from alarm_table where active= 1")
    suspend fun getActiveAlarm(): Flow<List<Alarm>>

    @Query("select * from alarm_table where active= 0")
    suspend fun getNotActiveAlarm(): Flow<List<Alarm>>

    @Query("SELECT * FROM alarm_table WHERE id = :id")
    suspend fun getAlarm(id: Int): Alarm
}