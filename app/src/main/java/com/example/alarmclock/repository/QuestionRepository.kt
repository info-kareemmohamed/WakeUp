package com.example.alarmclock.repository

import com.example.alarmclock.data.question.Question

class QuestionRepository {

    private val sampleQuestion = Question(
        questionText = "5 + 4",
        answerOptions = listOf("9", "8", "7"),
        correctAnswerIndex = 0
    )


    fun getQuestion(): Question {
        return sampleQuestion
    }
}