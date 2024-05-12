package com.example.alarmclock.alarmmanager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.alarmclock.data.entity.Alarm
import java.util.Calendar

class AndriodAlarmScheduler(private val context: Context) : AlarmScheduler {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    override fun scheduler(item: Alarm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                getCalendar(item.time).timeInMillis,
               getPendingIntent(item)
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                getCalendar(item.time).timeInMillis,
                AlarmManager.INTERVAL_DAY,
                getPendingIntent(item)

            )
        }
    }


    override fun cancel(item: Alarm) {
        alarmManager.cancel(getPendingIntent(item))
    }




    private fun getPendingIntent(alarm: Alarm):PendingIntent{
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.tester.alarmmanager"
        }
        return PendingIntent.getBroadcast(
            context,
            alarm.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    }

    private fun getCalendar(time:String): Calendar {
        val hour = time.substring(0, 2).toInt()
        val minute = time.substring(3, 5).toInt()
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

    }


}