package com.example.brainbrewai.presentation.chat

import com.example.brainbrewai.data.model.ChatMessage

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false
)