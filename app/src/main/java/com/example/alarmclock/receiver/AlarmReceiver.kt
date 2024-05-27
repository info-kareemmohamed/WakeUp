package com.example.alarmclock.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.core.Notification


class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("com.tester.alarmmanager")) {

            val alarmId = intent?.getIntExtra("alarmId", 1)
            val message = intent?.getStringExtra("message") ?: "No message available"
            val time = intent?.getStringExtra("time") ?: "Unknown time"

            val notificationTitle = "Alarm Triggered : $time"
            val notificationDescription =
                "Message: $message"
            Notification(
                NotificationId = alarmId!!, // Unique ID for the notification
                title = notificationTitle,
                description = notificationDescription,
                stopAlarmPendingIntent = AndroidAlarmScheduler(context!!).createStopAlarm(
                    context,
                    alarmId,
                    message,
                    time
                )

            ).displayNotification(context)
            Log.d("wwwwwwwwww", "wwwwwwwwwwwttt")


        } else if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {

            //    context?.startService(Intent(context, RestartAlarmsService::class.java))
            AndroidAlarmScheduler(context!!).restartAllAlarms(context)

        }


    }

}

