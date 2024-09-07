package com.example.alarmclock.domain.use_case

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.alarmclock.core.Constant
import com.example.alarmclock.core.Constant.NOTIFY_BEFORE_ALARM_TIME
import com.example.alarmclock.core.getPendingIntent
import com.example.alarmclock.data.model.Alarm
import java.util.Calendar
import javax.inject.Inject


class SchedulerAlarmUseCase @Inject constructor() {

    operator fun invoke(
        item: Alarm?,
        context: Context,
        alarmAction: String = Constant.ACTION_ALARM_MANAGER
    ) {
        if (item == null) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        item.getDaysOfWeek().forEach { day ->
            val calendar = getCalendar(item.hour, item.minute, day, alarmAction)
            val pendingIntent = item.getPendingIntent(context, day, alarmAction)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        }
    }


    private fun getCalendar(hour: Int, minute: Int, day: Int, alarmAction: String): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            /*
            for NotifyBeforeAlarmTriggerService
              Restoring the original alarm time so that if it has been shifted to the next week,
               the notification time will move with it. After that, the notify-before time is subtracted.
             */
            add(
                Calendar.MINUTE,
                if (alarmAction != Constant.ACTION_ALARM_MANAGER) NOTIFY_BEFORE_ALARM_TIME else 0
            )


            // If the scheduled time has passed, adjust the calendar to the next week
            if (timeInMillis < System.currentTimeMillis()) {
                Log.d("AlarmActivity", "Next week")
                add(Calendar.DAY_OF_MONTH, 7)
            }

            //for NotifyBeforeAlarmTriggerService
            add(
                Calendar.MINUTE,
                if (alarmAction != Constant.ACTION_ALARM_MANAGER) -NOTIFY_BEFORE_ALARM_TIME else 0
            )

        }
    }
}