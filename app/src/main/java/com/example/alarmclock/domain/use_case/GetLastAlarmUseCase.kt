package com.example.alarmclock.domain.use_case

import com.example.alarmclock.domain.repository.AlarmRepository
import javax.inject.Inject

class GetLastAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository)  {
   suspend operator fun invoke() = alarmRepository.getLastAlarm()
}