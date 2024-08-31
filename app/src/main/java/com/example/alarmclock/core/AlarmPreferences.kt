package com.example.alarmclock.core

import android.content.Context
import android.content.SharedPreferences
import com.example.alarmclock.core.Constant.ALARM_FOREGROUND_SERVICE_KEY
import com.example.alarmclock.core.Constant.ALARM_NUMBER_TRIGGER_KEY
import com.example.alarmclock.core.Constant.PREFS_NAME

/**
 * AlarmPreferences manages the count of active alarms in the application.
 * It uses SharedPreferences to persist and retrieve the number of active alarms,
 * and handles scenarios where multiple alarms might be triggered simultaneously or sequentially.
 * This ensures that notifications are managed correctly, avoiding conflicts or duplicates.
 * SharedPreferences is used to effectively manage this data persistently.
 */

class AlarmPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setForegroundServiceId(id: Int) {
        sharedPreferences.edit().putInt(ALARM_FOREGROUND_SERVICE_KEY, id).apply()
    }

    fun getForegroundServiceId(): Int {
        return sharedPreferences.getInt(ALARM_FOREGROUND_SERVICE_KEY, 0)
    }

    fun getAlarmCount(): Int {
        return sharedPreferences.getInt(ALARM_NUMBER_TRIGGER_KEY, 0)
    }

    fun incrementAlarmCount() {
        val currentCount = getAlarmCount()
        sharedPreferences.edit().putInt(ALARM_NUMBER_TRIGGER_KEY, currentCount + 1).apply()
    }

    fun decrementAlarmCount() {
        val currentCount = getAlarmCount()
        if (currentCount > 0) {
            sharedPreferences.edit().putInt(ALARM_NUMBER_TRIGGER_KEY, currentCount - 1).apply()
        }
    }

    fun reset() {
        sharedPreferences.edit().putInt(ALARM_NUMBER_TRIGGER_KEY, 0).apply()
        sharedPreferences.edit().putInt(ALARM_FOREGROUND_SERVICE_KEY, 0).apply()

    }
}
