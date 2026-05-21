package com.example.brainbrewai.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.model.ChatMessage
import com.example.brainbrewai.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val repository = ChatRepository()

    private val _messages =
        MutableStateFlow<List<ChatMessage>>(emptyList())

    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> = _isLoading

    fun sendMessage(userMessage: String) {

        if (userMessage.isBlank()) return

        val updatedMessages =
            _messages.value.toMutableList()

        updatedMessages.add(
            ChatMessage(
                message = userMessage,
                isUser = true
            )
        )

        _messages.value = updatedMessages

        viewModelScope.launch {

            _isLoading.value = true

            try {

                val aiReply =
                    repository.askAI(userMessage)

                val aiMessages =
                    _messages.value.toMutableList()

                aiMessages.add(
                    ChatMessage(
                        message = aiReply,
                        isUser = false
                    )
                )

                _messages.value = aiMessages

            } catch (e: Exception) {

                val errorMessages =
                    _messages.value.toMutableList()

                errorMessages.add(
                    ChatMessage(
                        message = "Error: ${e.message}",
                        isUser = false
                    )
                )

                _messages.value = errorMessages

            } finally {

                _isLoading.value = false
            }
        }
    }
}