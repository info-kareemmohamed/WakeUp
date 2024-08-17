package com.example.alarmclock.domain.use_case

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import com.example.alarmclock.common.Constant.SNOOZE_TIME
import com.example.alarmclock.common.getPendingIntent
import com.example.alarmclock.data.model.Alarm
import java.util.Calendar
import javax.inject.Inject


class SnoozeAlarmUseCase @Inject constructor() {
    operator fun invoke(context: Context, alarm: Alarm) {
        val calendar = Calendar.getInstance().apply { add(Calendar.MINUTE, SNOOZE_TIME) }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = alarm.getPendingIntent(context, calendar.get(Calendar.DAY_OF_WEEK))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }
}