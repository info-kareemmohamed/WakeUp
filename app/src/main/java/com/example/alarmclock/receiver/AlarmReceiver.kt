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

            Notification(
                NotificationId = 1, // Unique ID for the notification
                title = "Alarm Triggered",
                description = "Your alarm has been triggered!"
            ).displayNotification(context!!)

            Log.d("wwwwwwwwww", "wwwwwwwwwwwttt")


        } else if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {

            //    context?.startService(Intent(context, RestartAlarmsService::class.java))
            AndroidAlarmScheduler(context!!).restartAllAlarms(context)

        }


    }

}

