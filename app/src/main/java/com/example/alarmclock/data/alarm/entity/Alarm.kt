package com.example.alarmclock.data.alarm.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


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
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        message = parcel.readString() ?: "",
        days = parcel.readString() ?: "",
        hour = parcel.readString() ?: "",
        minute = parcel.readString() ?: "",
        active = parcel.readByte() != 0.toByte(),
        modeIcon = parcel.readInt(),
        timePeriod = parcel.readString() ?: ""
    ) {
    }

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

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(message)
        parcel.writeString(days)
        parcel.writeString(hour)
        parcel.writeString(minute)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeInt(modeIcon)
        parcel.writeString(timePeriod)
    }

    companion object CREATOR : Parcelable.Creator<Alarm> {
        override fun createFromParcel(parcel: Parcel): Alarm {
            return Alarm(parcel)
        }

        override fun newArray(size: Int): Array<Alarm?> {
            return arrayOfNulls(size)
        }
    }


}
