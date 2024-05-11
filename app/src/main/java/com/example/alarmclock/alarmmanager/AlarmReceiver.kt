package com.example.alarmclock.alarmmanager


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarmclock.core.Notification


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Alarm", "Time to wake up!")
        Notification(
            title = "Alarm",
            description = "Time to wake up!",
            NotificationId = 1
        ).displayNotification(context!!)
    }
}
