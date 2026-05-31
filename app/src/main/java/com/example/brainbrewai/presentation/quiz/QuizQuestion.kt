package com.example.brainbrewai.presentation.quiz

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String = "",
    var selectedAnswer: String? = null
)