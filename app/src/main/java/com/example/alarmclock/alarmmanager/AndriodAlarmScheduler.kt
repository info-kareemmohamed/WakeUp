package com.example.alarmclock.alarmmanager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.alarmclock.data.entity.Alarm
import com.example.alarmclock.receiver.AlarmReceiver
import com.example.alarmclock.ui.AlarmNotification
import java.util.Calendar

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    override fun scheduler(item: Alarm?) {
        item?.let {
            val daysOfWeek = it.getDaysOfWeek()
            for (day in daysOfWeek) {
                val calendar = getCalendar(it.hour.toInt(), it.minute.toInt(),  it.timePeriod,day)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        getPendingIntent(it, day)
                    )
                } else {
                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        AlarmManager.INTERVAL_DAY * 7,  // Set to repeat weekly
                        getPendingIntent(it, day)
                    )
                }
            }
        }
    }

    override fun cancel(item: Alarm) {
        val daysOfWeek = item.getDaysOfWeek()
        for (day in daysOfWeek) {
            alarmManager.cancel(getPendingIntent(item, day))
        }
    }

    private fun getPendingIntent(alarm: Alarm, day: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.tester.alarmmanager"
            putExtra("alarmId", alarm.id)
            putExtra("dayOfWeek", day)
        }
        return PendingIntent.getBroadcast(
            context,
            alarm.id * 10 + day,  // Unique requestCode for each day
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
    fun createStopAlarm(context: Context) : PendingIntent {
        val intent = Intent(context, AlarmNotification::class.java)

        val reqCode = "stopalarm".hashCode()
        return PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_MUTABLE)
    }

    fun createButtonReceiver(context: Context) : PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("NOTIFICATION_ID", "CHANNEL_ID")
        intent.putExtra("ACTION", "off")
        val reqCode = "btnreceiver".hashCode()
        return PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_MUTABLE)
    }

    private fun getCalendar(hour: Int, minute: Int, timePeriod: String,day: Int): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, day)
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.AM_PM, if (timePeriod == "AM") Calendar.AM else Calendar.PM)
        }

        // If the time has already passed for today, move to the next week
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 7)
        }

        return calendar
    }
}