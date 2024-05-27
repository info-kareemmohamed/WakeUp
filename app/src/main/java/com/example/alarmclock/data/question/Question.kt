package com.example.alarmclock.data.question

data class Question(
    val questionText: String,
    val answerOptions: List<String>,
    val correctAnswerIndex: Int
)