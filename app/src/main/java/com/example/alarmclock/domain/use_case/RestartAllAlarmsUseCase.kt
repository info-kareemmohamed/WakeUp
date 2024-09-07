package com.example.alarmclock.domain.use_case

import android.content.Context
import com.example.alarmclock.data.local.alarm.dao.AlarmDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestartAllAlarmsUseCase @Inject constructor(
    private val alarmDao: AlarmDao,
    private val schedulerAlarmUseCase: SchedulerAlarmUseCase,
    private val notifyBeforeAlarmTriggerUseCase: NotifyBeforeAlarmTriggerUseCase
) {
    operator fun invoke(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val activeAlarms = alarmDao.getActiveAlarm()
            activeAlarms.forEach { alarm ->
                schedulerAlarmUseCase(alarm, context)
                notifyBeforeAlarmTriggerUseCase(alarm, context)
            }
        }
    }
}