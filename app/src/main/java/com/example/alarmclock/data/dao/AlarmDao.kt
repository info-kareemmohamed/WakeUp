package com.example.alarmclock.data.dao

import androidx.lifecycle.LiveData
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
     fun getAlarm(): LiveData<List<Alarm>>

    @Query("select * from alarm_table where active= 1")
    fun getActiveAlarm(): LiveData<List<Alarm>>

    @Query("select * from alarm_table where active= 0")
    fun getNotActiveAlarm(): LiveData<List<Alarm>>

    @Query("SELECT * FROM alarm_table WHERE id = :id")
    fun getAlarm(id: Int): Alarm
}