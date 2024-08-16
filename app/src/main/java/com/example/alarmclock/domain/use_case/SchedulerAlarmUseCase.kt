package com.example.alarmclock.domain.use_case

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.alarmclock.common.getPendingIntent
import com.example.alarmclock.data.model.Alarm
import java.util.Calendar


/**
 * This use case is responsible for scheduling alarms based on the provided Alarm data.
 * For devices running Android M (API 23) and above, it uses setExactAndAllowWhileIdle to ensure
 * the alarm triggers exactly at the specified time even in low-power idle modes.
 * For older devices, it falls back to setRepeating for daily repetition.
 */

class SchedulerAlarmUseCase {

    operator fun invoke(item: Alarm?, context: Context) {
        if (item == null) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        item.getDaysOfWeek().forEach { day ->
            val calendar = getCalendar(item.hour, item.minute, day)
            val pendingIntent = item.getPendingIntent(context, day)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                // setRepeating consumes fewer resources but setExact ensures precise timing
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            }
        }
    }


    private fun getCalendar(hour: Int, minute: Int, day: Int): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If the scheduled time has passed, adjust the calendar to the next week
            if (timeInMillis < System.currentTimeMillis()) {
                Log.d("AlarmActivity", "Next week")
                add(Calendar.DAY_OF_MONTH, 7)
            }
        }
    }
}