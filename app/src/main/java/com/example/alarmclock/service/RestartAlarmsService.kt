package com.example.alarmclock.service


import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.alarmclock.viewmodel.AlarmViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RestartAlarmsService : Service() {
    lateinit var viewModel: AlarmViewModel
    override fun onCreate() {
        super.onCreate()
        viewModel = AlarmViewModel(applicationContext as Application)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        CoroutineScope(Dispatchers.IO).launch {
            val  activeAlarms = viewModel.getActiveAlarm()

        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}
