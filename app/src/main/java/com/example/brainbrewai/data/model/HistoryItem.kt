package com.example.brainbrewai.data.model

data class HistoryItem(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val type: String = "",
    val timestamp: Long = 0L
)