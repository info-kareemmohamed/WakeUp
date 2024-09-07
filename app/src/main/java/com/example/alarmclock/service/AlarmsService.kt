package com.example.alarmclock.service


import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.example.alarmclock.R
import com.example.alarmclock.core.AlarmPreferences
import com.example.alarmclock.core.AlarmSound
import com.example.alarmclock.core.Constant.EXTRA_ID
import com.example.alarmclock.core.Constant.EXTRA_SOUND
import com.example.alarmclock.core.cancelNotification
import com.example.alarmclock.core.createNotificationFromIntent


class AlarmsService : Service() {

    private var alarmSound: AlarmSound? = null
    private lateinit var alarmPreferences: AlarmPreferences

    override fun onCreate() {
        alarmPreferences = AlarmPreferences(this)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when (AlarmActions.valueOf(action)) {
                AlarmActions.Activate -> activate(intent)
                AlarmActions.Deactivate -> deactivate(intent.getIntExtra(EXTRA_ID, 0))
            }
        }
        return START_STICKY
    }


    private fun deactivate(id: Int) {
        alarmPreferences.decrementAlarmCount()
        cancelNotification(this, id)
        if (alarmPreferences.getAlarmCount() == 0)
            stopSelf()
        else
            if (alarmPreferences.getForegroundServiceId() == id)
                stopForegroundCompat()
    }


    private fun activate(intent: Intent?) {
        Log.d("wwwwww", "${alarmPreferences.getAlarmCount()}")
        val id = intent?.getIntExtra(EXTRA_ID, 0) ?: return
        val soundResId = intent.getIntExtra(EXTRA_SOUND, R.raw.default_sound)
        val notification = createNotificationFromIntent(intent, this)

        if (alarmPreferences.getAlarmCount() == 0) {
            alarmPreferences.setForegroundServiceId(id)
            startForeground(id, notification)

        } else {
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                id,
                notification
            )
        }
        alarmPreferences.incrementAlarmCount()
        alarmSound?.release()
        alarmSound = AlarmSound(this, soundResId).apply { startSound() }
    }


    private fun stopForegroundCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(false)
        }
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        alarmSound?.release()
        alarmPreferences.reset()
        super.onDestroy()
    }

    enum class AlarmActions {
        Activate,
        Deactivate
    }
}