package com.example.alarmclock.domain.use_case

import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(alarm: Alarm) = alarmRepository.deleteAlarm(alarm)
}