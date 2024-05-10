package com.example.alarmclock.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.alarmclock.entity.Alarm
import java.util.Calendar

class AndriodAlarmScheduler(private val context: Context) : AlarmScheduler {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduler(item: Alarm) {
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 3)
        calendar.set(Calendar.MINUTE, 23)
        calendar.set(Calendar.SECOND, 0)
        val intent: Intent = Intent(context, AlarmReceiver::class.java)
        intent.action = "com.tester.alarmmanager"
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            )

        )
    }

    override fun cancel(item: Alarm) {
        TODO("Not yet implemented")
    }
}