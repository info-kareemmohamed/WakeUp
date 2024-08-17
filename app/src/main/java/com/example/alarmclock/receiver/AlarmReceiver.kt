package com.example.alarmclock.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.alarmclock.common.Constant
import com.example.alarmclock.common.getAlarmFromIntent
import com.example.alarmclock.domain.use_case.RestartAllAlarmsUseCase
import com.example.alarmclock.service.AlarmsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var restartAllAlarmsUseCase: RestartAllAlarmsUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->
            when (intent?.action) {
                // If the action is related to the alarm manager, handle the alarm trigger
                Constant.ACTION_ALARM_MANAGER -> handleAlarmAction(ctx, intent)
                // If the device has just booted, restart all alarms
                Intent.ACTION_BOOT_COMPLETED -> restartAllAlarmsUseCase(ctx)
            }
        }


    }

    private fun handleAlarmAction(context: Context, intent: Intent) {
        Intent(context, AlarmsService::class.java).apply {
            val alarm=intent.getAlarmFromIntent()
            putExtra(Constant.EXTRA_ID, alarm?.id)
            putExtra(Constant.EXTRA_MESSAGE, alarm?.message)
            putExtra(Constant.EXTRA_SOUND, alarm?.sound)
            ContextCompat.startForegroundService(context, this)
        }
    }
}

