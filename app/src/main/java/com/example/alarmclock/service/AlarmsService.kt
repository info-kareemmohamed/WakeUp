package com.example.alarmclock.service


import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.alarmclock.R
import com.example.alarmclock.common.AlarmSound
import com.example.alarmclock.common.Constant.EXTRA_ID
import com.example.alarmclock.common.Constant.EXTRA_MESSAGE
import com.example.alarmclock.common.Constant.EXTRA_SOUND
import com.example.alarmclock.common.Constant.EXTRA_TIME
import com.example.alarmclock.common.Notification
import com.example.alarmclock.common.formatCurrentTime

import com.example.alarmclock.presentation.alarm_challenge_screen.ui.AlarmChallengeScreen
import java.util.Calendar


class AlarmsService : Service() {

    private  var alarmSound: AlarmSound?=null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        val id = intent?.getIntExtra(EXTRA_ID, 0) ?: return START_NOT_STICKY
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: ""
        val soundResId = intent.getIntExtra(EXTRA_SOUND, R.raw.default_sound)
        val time = Calendar.getInstance().formatCurrentTime()

        startForeground(id, Notification(
            NotificationId = id,
            title = "Alarm Triggered : $time",
            description = "Message: $message",
            openAlarmChallengeScreenIntent = createAlarmChallengeScreenIntent(this, time, id)
        ).getAndDisplayNotification(this))

        alarmSound?.release()
        alarmSound = AlarmSound(this, soundResId).apply { startSound() }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onDestroy() {
        alarmSound?.release()
        super.onDestroy()
    }


    private fun createAlarmChallengeScreenIntent(
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


}
