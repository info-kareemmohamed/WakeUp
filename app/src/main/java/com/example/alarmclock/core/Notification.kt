package com.example.alarmclock.core

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.alarmclock.R
import com.example.alarmclock.core.Constant.EXTRA_ID
import com.example.alarmclock.core.Constant.EXTRA_MESSAGE
import com.example.alarmclock.core.Constant.EXTRA_TIME
import com.example.alarmclock.core.Constant.NOTIFICATION_CHANNEL_ID
import com.example.alarmclock.presentation.alarm_challenge_screen.ui.AlarmChallengeScreen
import java.util.Calendar


fun createNotification(
    context: Context,
    title: String,
    description: String,
    openAlarmChallengeScreenIntent: PendingIntent? = null
): Notification {

    return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID).apply {
        setSmallIcon(R.drawable.baseline_alarm_add_24)
        setContentTitle(title)
        setContentText(description)
        setPriority(NotificationCompat.PRIORITY_HIGH)
        setCategory(NotificationCompat.CATEGORY_ALARM)
        setOngoing(true)
        openAlarmChallengeScreenIntent?.let {
            setFullScreenIntent(it, true)
        }
    }.build()


}


fun cancelNotification(context: Context, notificationId: Int) {
    val notificationManager = ContextCompat.getSystemService(
        context,
        NotificationManager::class.java
    ) as NotificationManager
    notificationManager.cancel(notificationId)
}


fun createNotificationFromIntent(intent: Intent?, context: Context): Notification {
    val id = intent?.getIntExtra(EXTRA_ID, 0) ?: 0
    val message = intent?.getStringExtra(EXTRA_MESSAGE).orEmpty()
    val time = Calendar.getInstance().formatCurrentTime()
    return createNotification(
        context,
        "Alarm Triggered : $time",
        "Message: $message",
        createAlarmChallengeScreenIntent(context, time, id)
    )
}


fun createAlarmChallengeScreenIntent(
    context: Context,
    time: String,
    id: Int,
): PendingIntent {
    val intent = Intent(context, AlarmChallengeScreen::class.java).apply {
        putExtra(EXTRA_TIME, time)
        putExtra(EXTRA_ID, id)
    }

    val requestCode = "openAlarmChallengeScreen$time${id}".hashCode()
    return PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_MUTABLE)
}
