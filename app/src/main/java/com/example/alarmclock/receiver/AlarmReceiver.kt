package com.example.alarmclock.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.core.Constant
import com.example.alarmclock.core.Notification
import com.example.alarmclock.data.alarm.entity.Alarm
import com.example.alarmclock.service.AlarmsService


class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Constant.ACTION_ALARM_MANAGER)) {

            var alarm = getAlarmFromIntent(intent)
            val time = intent?.getStringExtra(Constant.EXTRA_TIME) ?: "Unknown time"


//            var notification = Notification(
//                NotificationId = alarm?.id ?: 1, // Unique ID for the notification
//                title = "Alarm Triggered : $time",
//                description = "Message: ${alarm?.message}",
//                stopAlarmPendingIntent = AndroidAlarmScheduler(context!!).createStopAlarm(
//                    context,
//                    time,
//                    alarm?.id ?: 1
//                )
//
//            ).getAndDisplayNotification(context)

            // Start the RestartAlarmsService
            val serviceIntent = Intent(context, AlarmsService::class.java)
            serviceIntent.putExtra(Constant.EXTRA_ALARM, alarm)
            serviceIntent.putExtra(Constant.EXTRA_TIME, time)
            ContextCompat.startForegroundService(context!!, serviceIntent)

            Log.d("wwwwwwwwww", "wwwwwwwwwwwttt")


        } else if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {

            //context?.startService(Intent(context, RestartAlarmsService::class.java))
            AndroidAlarmScheduler(context!!).restartAllAlarms(context)

        }


    }

    private fun getAlarmFromIntent(intent: Intent?): Alarm? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Constant.EXTRA_ALARM, Alarm::class.java)
        } else {
            intent?.getParcelableExtra(Constant.EXTRA_ALARM)
        }
    }

}

