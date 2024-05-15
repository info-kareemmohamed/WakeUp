package com.example.alarmclock.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "alarm_table")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    var message: String,
    var days: String,
    var hour: String,
    var minute: String,
    var active: Boolean = true,
    val modeIcon: Int,
    var abbreviations: String
)
