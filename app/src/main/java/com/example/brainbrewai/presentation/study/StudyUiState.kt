package com.example.brainbrewai.presentation.study

data class StudyUiState(
    val activePlan: String = "",
    val todayGoal: String = "",
    val upcomingRevision: String = "",
    val completedDays: Int = 0,
    val totalDays: Int = 0
)