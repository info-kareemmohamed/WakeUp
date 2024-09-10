package com.example.alarmclock.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.alarmclock.R
import com.example.alarmclock.core.Constant.EXTRA_TIME
import com.example.alarmclock.core.Constant.NOTIFICATION_CHANNEL_ID


class NotifyBeforeAlarmTriggerService : Service() {

    override fun onBind(intent: Intent): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            MessageActions.Activate.toString() -> activate(intent)
            MessageActions.Deactivate.toString() -> deactivate()
        }

        return START_STICKY
    }

    private fun activate(intent: Intent?) {
        val time = intent?.getStringExtra(EXTRA_TIME)
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.baseline_alarm_add_24)
            setContentTitle("Alarm Trigger")
            setContentText("Alarm will trigger in $time")
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setCategory(NotificationCompat.CATEGORY_ALARM)
        }.build()


            startForeground(1, notification)

    }

    private fun deactivate() {
            stopSelf()
    }


    enum class MessageActions {
        Activate,
        Deactivate
    }

}