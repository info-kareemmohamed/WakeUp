package com.example.alarmclock.di

import android.app.Application
import androidx.room.Room
import com.example.alarmclock.common.core.Constant.DATABASE_NAME
import com.example.alarmclock.data.local.alarm.database.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAlarmDatabase(application: Application): AlarmDatabase {
        return Room.databaseBuilder(application, AlarmDatabase::class.java,DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(alarmDatabase: AlarmDatabase) = alarmDatabase.alarmDao()


}