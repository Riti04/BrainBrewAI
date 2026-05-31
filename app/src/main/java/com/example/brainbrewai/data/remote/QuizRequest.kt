package com.example.brainbrewai.data.remote

data class QuizRequest(
    val topic: String,
    val total_questions: Int
)