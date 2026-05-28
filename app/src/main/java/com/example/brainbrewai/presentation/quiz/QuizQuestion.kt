package com.example.brainbrewai.presentation.quiz

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    var selectedAnswer: String? = null
)