package com.example.alarmclock.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.receiver.AlarmReceiver
import java.util.Calendar

fun Alarm.getPendingIntent(context: Context, day: Int,alarmAction:String= Constant.ACTION_ALARM_MANAGER): PendingIntent {
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        action = alarmAction
        putExtra(Constant.EXTRA_ALARM, this@getPendingIntent)

    }
    return PendingIntent.getBroadcast(
        context,
        id * 10 + day,  // Unique requestCode for each day
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

fun Calendar.formatCurrentTime(): String {
    // Get hour in 12-hour format, treating 0 as 12 (for midnight)
    val hour = if (get(Calendar.HOUR) == 0) 12 else get(Calendar.HOUR) % 12
    // Format hour and minute to two digits
    val formattedHour = String.format("%02d", hour)
    val formattedMinute = String.format("%02d", get(Calendar.MINUTE))
    // Determine AM or PM
    val amPm = if (get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
    return "$formattedHour:$formattedMinute $amPm"
}
fun Intent?.getAlarmFromIntent(): Alarm? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.getParcelableExtra(Constant.EXTRA_ALARM, Alarm::class.java)
    } else {
        this?.getParcelableExtra(Constant.EXTRA_ALARM)
    }
}


fun dayNameToCalendar(day: String): String {
    return when (day) {
        "Sun" -> "${Calendar.SUNDAY}"
        "Mon" -> "${Calendar.MONDAY}"
        "Tue" -> "${Calendar.TUESDAY}"
        "Wed" -> "${Calendar.WEDNESDAY}"
        "Thu" -> "${Calendar.THURSDAY}"
        "Fri" -> "${Calendar.FRIDAY}"
        "Sat" -> "${Calendar.SATURDAY}"
        else -> ""
    }

}

fun calendarToDayName(day: Int): String {
    return when (day) {
        Calendar.SUNDAY -> "Sun"
        Calendar.MONDAY -> "Mon"
        Calendar.TUESDAY -> "Tue"
        Calendar.WEDNESDAY -> "Wed"
        Calendar.THURSDAY -> "Thu"
        Calendar.FRIDAY -> "Fri"
        Calendar.SATURDAY -> "Sat"
        else -> ""
    }
}

