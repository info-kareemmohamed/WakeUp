package com.example.alarmclock.data.alarm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alarmclock.data.alarm.dao.AlarmDao
import com.example.alarmclock.data.alarm.entity.Alarm
import kotlin.concurrent.Volatile

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var instance: AlarmDatabase? = null

        fun getDatabace(context: Context): AlarmDatabase {
            if (instance != null) return instance!!
            synchronized(this) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmDatabase::class.java,
                    "AlarmDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                return instance!!
            }


        }


    }
}