package com.example.brainbrewai.presentation.quiz

data class QuizContent(
    val mcqs: List<QuizQuestion> = emptyList(),
    val shortAnswers: List<Pair<String, String>> = emptyList(),
    val interviewQuestions: List<Pair<String, String>> = emptyList()
)