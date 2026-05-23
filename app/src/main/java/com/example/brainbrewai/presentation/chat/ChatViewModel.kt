package com.example.brainbrewai.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.model.ChatMessage
import com.example.brainbrewai.data.remote.ChatRequest
import com.example.brainbrewai.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages =
        MutableStateFlow<List<ChatMessage>>(emptyList())

    val messages: StateFlow<List<ChatMessage>>
            = _messages

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean>
            = _isLoading

    fun sendMessage(message: String) {

        if (message.isBlank()) return

        val userMessage = ChatMessage(
            text = message,
            isUser = true
        )

        _messages.value =
            _messages.value + userMessage

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val response =
                    RetrofitInstance.api.chatWithAI(
                        ChatRequest(message)
                    )

                if (response.isSuccessful) {

                    val body = response.body()

                    val aiText =
                        body?.response
                            ?: "No response from AI"

                    val aiMessage = ChatMessage(
                        text = aiText,
                        isUser = false
                    )

                    _messages.value =
                        _messages.value + aiMessage

                } else {

                    val errorMessage = ChatMessage(
                        text = "Server Error: ${response.code()}",
                        isUser = false
                    )

                    _messages.value =
                        _messages.value + errorMessage
                }

            } catch (e: Exception) {

                Log.e(
                    "CHAT_ERROR",
                    e.toString()
                )

                val errorMessage = ChatMessage(
                    text = "Error: ${e.localizedMessage}",
                    isUser = false
                )

                _messages.value =
                    _messages.value + errorMessage

            } finally {

                _isLoading.value = false
            }
        }
    }
}