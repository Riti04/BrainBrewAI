package com.example.brainbrewai.data.remote

data class StudyPlannerRequest(
    val goal: String,
    val days_left: Int,
    val daily_hours: Int
)