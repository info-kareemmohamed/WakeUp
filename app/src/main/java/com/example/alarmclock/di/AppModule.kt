package com.example.alarmclock.di

import com.example.alarmclock.data.local.alarm.dao.AlarmDao
import com.example.alarmclock.data.local.question.QuestionData
import com.example.alarmclock.data.repository.AlarmRepositoryImpl
import com.example.alarmclock.domain.repository.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDao: AlarmDao, questionData: QuestionData): AlarmRepository {
        return AlarmRepositoryImpl(alarmDao,questionData)
    }

    @Provides
    fun provideQuestionData(): QuestionData {
        return QuestionData()
    }




}