package com.example.alarmclock.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.alarmclock.R
import com.example.alarmclock.ui.AlarmNotification

class Notification(
    val NotificationName: String = "Alarm Notifications",
    val ChannelId: String = "alarm_notification_channel",
    val NotificationId: Int,
    var title: String,
    var description: String,
    var icon: Int = R.drawable.baseline_alarm_add_24
) {


    fun displayNotification(context: Context) {

        val notificationManager = getnotificationManager(context)
        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) notificationManager.createNotificationChannel(
            getNotificationChannel()
        )
        notificationManager.notify(NotificationId, getNotificationCompat(context))

    }

    private fun getnotificationManager(context: Context): NotificationManager {
        return ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            ChannelId,
            NotificationName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }


    private fun getPendingIntent(context: Context): PendingIntent {

        val activityIntent = Intent(context, AlarmNotification::class.java)
        return PendingIntent.getActivity(
            context,
            NotificationId,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getNotificationCompat(context: Context): Notification {
        return NotificationCompat.Builder(context, ChannelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.baseline_alarm_add_24)
            .setContentIntent(getPendingIntent(context))
            .setAutoCancel(true)
            .build()
    }
}