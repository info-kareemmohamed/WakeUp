package com.example.alarmclock.domain.use_case

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.alarmclock.common.getPendingIntent
import com.example.alarmclock.data.model.Alarm
import java.util.Calendar

class SchedulerAlarmUseCase {

    operator fun invoke(item: Alarm?, context: Context) {
        if (item == null) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        item.getDaysOfWeek().forEach { day ->
            val calendar = getCalendar(item.hour.toInt(), item.minute.toInt(), day)
            val pendingIntent = item.getPendingIntent(context, day)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setExactAlarm(alarmManager, calendar, pendingIntent)
            } else {
                setRepeatingAlarm(alarmManager, calendar, pendingIntent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setExactAlarm(alarmManager: AlarmManager, calendar: Calendar, pendingIntent: PendingIntent) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun setRepeatingAlarm(alarmManager: AlarmManager, calendar: Calendar, pendingIntent: PendingIntent) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )
    }

    private fun getCalendar(hour: Int, minute: Int, day: Int): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // Adjust to next week if the time has passed for today
            if (timeInMillis < System.currentTimeMillis()) {
                Log.d("AlarmActivity", "Next week")
                add(Calendar.DAY_OF_MONTH, 7)
            }
        }
    }
}