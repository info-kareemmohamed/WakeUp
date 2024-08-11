package com.example.alarmclock.di

import com.example.alarmclock.data.local.alarm.dao.AlarmDao
import com.example.alarmclock.data.local.question.QuestionData
import com.example.alarmclock.data.repository.AlarmRepositoryImpl
import com.example.alarmclock.domain.repository.AlarmRepository
import com.example.alarmclock.domain.use_case.AlarmUseCase
import com.example.alarmclock.domain.use_case.DeleteAlarmUseCase
import com.example.alarmclock.domain.use_case.GetActiveAlarmUseCase
import com.example.alarmclock.domain.use_case.GetAlarmByIdUseCase
import com.example.alarmclock.domain.use_case.GetAlarmUseCase
import com.example.alarmclock.domain.use_case.GetLastAlarmUseCase
import com.example.alarmclock.domain.use_case.GetNotActiveAlarmUseCase
import com.example.alarmclock.domain.use_case.InsertAlarmUseCase
import com.example.alarmclock.domain.use_case.UpdateAlarmUseCase
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

    @Provides
    @Singleton
    fun provideAlarmUseCase(alarmRepository: AlarmRepository): AlarmUseCase {
        return AlarmUseCase(
            insertAlarmUseCase = InsertAlarmUseCase(alarmRepository),
            deleteAlarmUseCase = DeleteAlarmUseCase(alarmRepository),
            updateAlarmUseCase = UpdateAlarmUseCase(alarmRepository),
            getLastAlarmUseCase = GetLastAlarmUseCase(alarmRepository),
            getAlarmByIdUseCase = GetAlarmByIdUseCase(alarmRepository),
            getActiveAlarmUseCase = GetActiveAlarmUseCase(alarmRepository),
            getNotActiveAlarmUseCase = GetNotActiveAlarmUseCase(alarmRepository),
            getAlarmUseCase = GetAlarmUseCase(alarmRepository)
        )
    }
}