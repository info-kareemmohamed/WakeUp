package com.example.alarmclock.alarmmanager


import android.app.Service

import android.content.Intent
import android.os.Build

import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.alarmclock.core.Notification


class RestartAlarmsService : Service() {
    private val CHANNEL_ID = "RestartAlarmsServiceChannel"

    override fun onCreate() {
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Notification(
            title = "Alarm",
            description = "",
            NotificationId = 1
        ).displayNotification(applicationContext)

        Toast.makeText(applicationContext, "yyyyyyyyyaaaaaa", Toast.LENGTH_LONG).show()


        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}
//viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//.create(AlarmViewModel::class.java)

//Notification(
//title = "Alarm",
//description = "",
//NotificationId = 1
//).displayNotification(applicationContext)
//
//Toast.makeText(applicationContext, "yyyyyyyyyaaaaaa", Toast.LENGTH_LONG).show()


//        activeAlarms?.let { alarms ->
//            val alarmScheduler = AndroidAlarmScheduler(applicationContext)
//            alarms.forEach { alarm ->
//                alarmScheduler.scheduler(alarm)
//            }
//        }