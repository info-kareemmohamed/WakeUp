package com.example.alarmclock.domain.use_case

import android.app.AlarmManager
import android.content.Context
import com.example.alarmclock.common.getPendingIntent
import com.example.alarmclock.data.model.Alarm
import javax.inject.Inject

class CancelAlarmUseCase @Inject constructor() {
    operator fun invoke(alarm: Alarm, context: Context) {
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.getDaysOfWeek().forEach{ day ->
            alarmManager.cancel(alarm.getPendingIntent(context,day))
        }
    }
}