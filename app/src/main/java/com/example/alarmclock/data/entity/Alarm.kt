package com.example.alarmclock.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "alarm_table")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var message: String,
    var days: String,
    var hour: String,
    var minute: String,
    var active: Boolean = true,
    val modeIcon: Int,
    var timePeriod: String
) {

    fun getDaysOfWeek(): List<Int> {
        return days.split(",").map { it.toInt() }
    }

    fun setDaysOfWeek(daysOfWeek: List<Int>) {
        days = daysOfWeek.joinToString(",")
    }




    fun getDayName(dayIndex: Int): String {
        val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        return if (dayIndex in 1..7) {
            daysOfWeek[dayIndex-1]
        } else {
            "Invalid"
        }
    }
    fun getDaysList(dayIndices: List<Int>): String {
        return dayIndices.joinToString(" , ") { getDayName(it) }
    }



}
