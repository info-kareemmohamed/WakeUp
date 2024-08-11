package com.example.alarmclock.common.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.alarmclock.R

class Notification(
    val NotificationName: String = "Alarm Notifications",
    val ChannelId: String = "alarm_notification_channel",
    val NotificationId: Int,
    var title: String,
    var description: String,
    val stopAlarmPendingIntent: PendingIntent,
    var icon: Int = R.drawable.baseline_alarm_add_24
) {

    fun getAndDisplayNotification(context: Context):Notification {
        val notificationManager = getNotificationManager(context)

        //  Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }
        var notificationBuilder=getNotificationBuilder(context).build()

        with(notificationManager) {
            notify(NotificationId, notificationBuilder)
        }
        return notificationBuilder
    }


    private fun getNotificationBuilder(context: Context): NotificationCompat.Builder {

        return NotificationCompat.Builder(context, ChannelId).apply {
            setSmallIcon(icon)
            setContentTitle(title)
            setContentText(description)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setCategory(NotificationCompat.CATEGORY_ALARM)
            setOngoing(true)

            setFullScreenIntent(stopAlarmPendingIntent, true)
        }


    }

    private fun getNotificationManager(context: Context): NotificationManager {
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
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = this@Notification.description
            setSound(null, null)
        }
    }


    companion object {

        fun cancelNotification(context: Context, notificationId: Int) {
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.cancel(notificationId)
        }
    }

}
