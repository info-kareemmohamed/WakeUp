package com.example.alarmclock.receiver



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.alarmclock.core.Notification
import com.example.alarmclock.service.RestartAlarmsService


class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("com.tester.alarmmanager")) {

            val notification = Notification(
                NotificationId = 1, // Unique ID for the notification
                title = "Alarm Triggered",
                description = "Your alarm has been triggered!"
            )

            // Display the notification
            notification.displayNotification(context!!)

             Toast.makeText(context, "yyyyyyyyyaaaaaa", Toast.LENGTH_LONG).show()


//            val buttonReceiverPendingIntent =
//                AndroidAlarmScheduler(context!!).createButtonReceiver(context)
//            val stopAlarmPendingIntent = AndroidAlarmScheduler(context!!).createStopAlarm(context)
//            val builder = NotificationCompat.Builder(context!!, "CHANNEL_ID").apply {
//                setSmallIcon(R.drawable.baseline_alarm_add_24)
//                setContentTitle("triggered.timeStr()")
//                setContentText("triggered.name")
//                setPriority(NotificationCompat.PRIORITY_HIGH)
//                setCategory(NotificationCompat.CATEGORY_ALARM)
//
//                setFullScreenIntent(stopAlarmPendingIntent, true)
//                addAction(R.drawable.baseline_alarm_add_24, "ddd", buttonReceiverPendingIntent)
//            }
//
//            val notificationManager =
//                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            with(notificationManager) {
//                val notification = builder.build()
//                notify(1, notification)
//            }


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