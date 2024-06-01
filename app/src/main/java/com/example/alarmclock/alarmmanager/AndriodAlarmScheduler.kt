package com.example.alarmclock.alarmmanager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.alarmclock.core.Constant
import com.example.alarmclock.data.alarm.AlarmDatabase
import com.example.alarmclock.data.alarm.entity.Alarm
import com.example.alarmclock.receiver.AlarmReceiver
import com.example.alarmclock.ui.AlarmNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    override fun scheduler(item: Alarm?) {
        item?.let {
            val daysOfWeek = it.getDaysOfWeek()
            for (day in daysOfWeek) {
                val calendar = getCalendar(it.hour.toInt(), it.minute.toInt(), it.timePeriod, day)
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
            action = Constant.ACTION_ALARM_MANAGER
            putExtra(Constant.EXTRA_ALARM, alarm)
            putExtra(
                Constant.EXTRA_TIME,
                handleTime(alarm.hour.toInt(), alarm.minute.toInt()) + " ${alarm.timePeriod}"
            )
        }
        return PendingIntent.getBroadcast(
            context,
            alarm.id * 10 + day,  // Unique requestCode for each day
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun handleTime(hourOfDay: Int, minuteOfDay: Int): String {
        val hour12 = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
        val formattedHour = String.format("%02d", hour12)
        val formattedMinute = String.format("%02d", minuteOfDay)
        return "$formattedHour:$formattedMinute"
    }

    fun createStopAlarm(context: Context, time: String, alarm: Alarm): PendingIntent {
        val intent = Intent(context, AlarmNotification::class.java).apply {
            putExtra(Constant.EXTRA_TIME, time)
            putExtra(Constant.EXTRA_ALARM, alarm)
        }

        val reqCode = "stopalarm$time${alarm.id}".hashCode()
        return PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_MUTABLE)
    }



    private fun getCalendar(hour: Int, minute: Int, timePeriod: String, day: Int): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // If the time has already passed for today, move to the next week
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 7)
            Log.d("wwwwwwwwwww", "Noooooo")

        }

        return calendar
    }


    fun restartAllAlarms(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val activeAlarms = AlarmDatabase.getDatabace(context).alarmDao().getActiveAlarm()
            activeAlarms.let { alarms ->
                val alarmScheduler = AndroidAlarmScheduler(context)
                alarms.forEach { alarm ->
                    alarmScheduler.scheduler(alarm)
                }
            }
        }
    }
}