package com.example.alarmclock.data.local.question

import com.example.alarmclock.data.model.Question
import kotlin.random.Random

class QuestionData {


    private fun getRandomQuestion(operator: String): Question {
        val number1 = Random.nextInt(1, 100)
        val number2 = Random.nextInt(1, 100)
        val res = when (operator) {
            "+" -> number1 + number2
            "-" -> number1 - number2
            else -> throw IllegalArgumentException("Unsupported operator")
        }
        val list: MutableList<String> = MutableList(3) { Random.nextInt(1, 200).toString() }
        val index = Random.nextInt(0, 3)
        list[index] = res.toString()
        return Question(
            questionText = "$number1 $operator $number2",
            answerOptions = list,
            correctAnswerIndex = index
        )
    }

    private fun getSumQuestion(): Question = getRandomQuestion("+")

    private fun getSubtractionQuestion(): Question = getRandomQuestion("-")

    fun getQuestion(): Question {
        return if (Random.nextInt(2) == 0) getSumQuestion() else getSubtractionQuestion()
    }
}