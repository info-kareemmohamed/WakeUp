package com.example.alarmclock.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.alarmclock.R
import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler

class Notification(
    val NotificationName: String = "Alarm Notifications",
    val ChannelId: String = "alarm_notification_channel",
    val NotificationId: Int,
    var title: String,
    var description: String,
    val stopAlarmPendingIntent:PendingIntent,
    var icon: Int = R.drawable.baseline_alarm_add_24
) {

    fun displayNotification(context: Context) {
        val notificationManager = getNotificationManager(context)

        //  Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }

        with(notificationManager) {
            notify(NotificationId, getNotificationBuilder(context).build())
        }
    }


    private fun getNotificationBuilder(context: Context): NotificationCompat.Builder {
        val buttonReceiverPendingIntent =
            AndroidAlarmScheduler(context).createButtonReceiver(context)
        return NotificationCompat.Builder(context, ChannelId).apply {
            setSmallIcon(icon)
            setContentTitle(title)
            setContentText(description)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setCategory(NotificationCompat.CATEGORY_ALARM)
            setOngoing(true)

            setFullScreenIntent(stopAlarmPendingIntent, true)
            addAction(R.drawable.baseline_alarm_add_24, "Solve", buttonReceiverPendingIntent)
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


}
