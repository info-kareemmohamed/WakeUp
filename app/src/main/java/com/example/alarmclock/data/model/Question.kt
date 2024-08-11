package com.example.alarmclock.data.model

data class Question(
    val questionText: String,
    val answerOptions: List<String>,
    val correctAnswerIndex: Int
)