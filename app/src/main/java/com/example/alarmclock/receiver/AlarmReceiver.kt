package com.example.alarmclock.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.alarmclock.core.Constant
import com.example.alarmclock.core.getAlarmFromIntent
import com.example.alarmclock.domain.use_case.RestartAllAlarmsUseCase
import com.example.alarmclock.service.AlarmsService
import com.example.alarmclock.service.NotifyBeforeAlarmTriggerService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import  com.example.alarmclock.service.NotifyBeforeAlarmTriggerService.MessageActions
import java.util.Calendar

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
                MessageActions.Activate.toString() -> handleMessageActions(ctx, intent)

            }
        }


    }

    private fun handleAlarmAction(context: Context, intent: Intent) {
        val alarm = intent.getAlarmFromIntent()

        val stopIntent = Intent(context, NotifyBeforeAlarmTriggerService::class.java).apply {
            action = MessageActions.Deactivate.toString()
        }
        context.stopService(stopIntent)

        Intent(context, AlarmsService::class.java).apply {
            action = AlarmsService.AlarmActions.Activate.toString()
            putExtra(Constant.EXTRA_ID, alarm?.id)
            putExtra(Constant.EXTRA_MESSAGE, alarm?.message)
            putExtra(Constant.EXTRA_SOUND, alarm?.sound)
            ContextCompat.startForegroundService(context, this)
        }

    }

    private fun handleMessageActions(context: Context, intent: Intent) {
        Intent(context, NotifyBeforeAlarmTriggerService::class.java).apply {
            action = MessageActions.Activate.toString()
            val alarm = intent.getAlarmFromIntent()
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, alarm?.hour?:1)
                set(Calendar.MINUTE, alarm?.minute?:1)
            }
            calendar.add(Calendar.MINUTE, +2)
            val formattedHour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
            val formattedMinute = String.format("%02d", calendar.get(Calendar.MINUTE))
            putExtra(Constant.EXTRA_TIME, "$formattedHour:$formattedMinute ${alarm?.timePeriod}")
            ContextCompat.startForegroundService(context, this)
        }
    }
}

