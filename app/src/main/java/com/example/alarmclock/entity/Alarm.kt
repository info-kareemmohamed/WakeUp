package com.example.alarmclock.entity

import java.util.Date


data class Alarm(var id:Int, var days: String, var time: String, var active:Boolean =true, val modeImage:Int, var abbreviations:String  )
