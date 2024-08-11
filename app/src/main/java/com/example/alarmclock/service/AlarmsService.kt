package com.example.alarmclock.service


import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.example.alarmclock.common.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.common.core.AlarmSound
import com.example.alarmclock.common.core.Constant
import com.example.alarmclock.common.core.Notification
import com.example.alarmclock.data.model.Alarm


class AlarmsService : Service() {

    private lateinit var alarmSound: AlarmSound


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        val alarm = getAlarmFromIntent(intent)
        val time = intent?.getStringExtra(Constant.EXTRA_TIME) ?: "Unknown time"
        val notification = Notification(
            NotificationId = alarm?.id ?: 1, // Unique ID for the notification
            title = "Alarm Triggered : $time",
            description = "Message: ${alarm?.message}",
            stopAlarmPendingIntent = AndroidAlarmScheduler(this).createStopAlarm(
                this,
                time,
                alarm?.id ?: 1
            )

        ).getAndDisplayNotification(this)

        startForeground(alarm?.id ?: 1, notification)


        alarmSound = AlarmSound(this, alarm!!.sound)
        alarmSound.startSound()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    private fun getAlarmFromIntent(intent: Intent?): Alarm? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Constant.EXTRA_ALARM, Alarm::class.java)
        } else {
            intent?.getParcelableExtra(Constant.EXTRA_ALARM)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        alarmSound.stopSound()
    }


}
