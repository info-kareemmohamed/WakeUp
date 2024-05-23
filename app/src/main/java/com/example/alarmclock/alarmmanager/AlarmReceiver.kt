package com.example.alarmclock.alarmmanager


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

import androidx.annotation.RequiresApi



class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("com.tester.alarmmanager")) {

            context?.startForegroundService(Intent(context, RestartAlarmsService::class.java))


        } else if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {

            context?.startForegroundService(Intent(context, RestartAlarmsService::class.java))

        }


    }
}

//
//                val viewModel =
//                    ViewModelProvider(context as ViewModelStoreOwner).get(AlarmViewModel::class.java)
//
//
//                val activeAlarms = viewModel.getActiveAlarm().value
//                activeAlarms?.let { alarms ->
//                    val alarmScheduler = AndroidAlarmScheduler(context)
//                    alarms.forEach { alarm ->
//                        alarmScheduler.scheduler(alarm)
//                    }
//                }


//                Notification(
//                    title = "Alarm",
//                    description = "wwwwwwwwwwww",
//                    NotificationId = 1
//                ).displayNotification(context!!)
//
//                Toast.makeText(context, "yyyyyyyyyaaaaaa", Toast.LENGTH_LONG).show()