package com.example.alarmclock.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarmclock.R
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
@Entity(tableName = "alarm_table")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var message: String,
    var days: String,
    var hour: Int,
    var minute: Int,
    var active: Boolean = true,
    val sound: Int,
    var timePeriod: String,
    val modeIcon: Int = if (timePeriod == "AM") R.drawable.ic_sunny else R.drawable.ic_nightlight,
    ) : Parcelable {

    fun getDaysOfWeek(): List<Int> {
        return days.split(",").map { it.toInt() }
    }

    fun setDaysOfWeek(daysOfWeek: List<Int>) {
        days = daysOfWeek.joinToString(",")
    }

    fun getDayName(dayIndex: Int): String {
        val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        return if (dayIndex in 1..7) {
            daysOfWeek[dayIndex - 1]
        } else {
            "Invalid"
        }
    }

    fun getDaysList(dayIndices: List<Int>): String {
        return dayIndices.joinToString(" , ") { getDayName(it) }
    }

    fun getDaysList(): String {
        return getDaysOfWeek().joinToString(" , ") { getDayName(it) }
    }


}
