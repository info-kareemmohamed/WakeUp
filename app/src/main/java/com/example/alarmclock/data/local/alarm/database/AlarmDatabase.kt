package com.example.alarmclock.data.local.alarm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarmclock.data.local.alarm.dao.AlarmDao
import com.example.alarmclock.data.model.Alarm

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}