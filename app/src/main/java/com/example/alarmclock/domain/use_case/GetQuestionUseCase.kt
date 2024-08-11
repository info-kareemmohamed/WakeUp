package com.example.alarmclock.domain.use_case

import com.example.alarmclock.data.local.question.QuestionData
import com.example.alarmclock.data.model.Question
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(private val questionData: QuestionData) {
    operator fun invoke(): Question = questionData.getQuestion()
}