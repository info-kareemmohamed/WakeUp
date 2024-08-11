package com.example.alarmclock.domain.use_case

import com.example.alarmclock.domain.repository.AlarmRepository
import javax.inject.Inject

class GetNotActiveAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
 operator fun invoke() = alarmRepository.getNotActiveAlarm()
}