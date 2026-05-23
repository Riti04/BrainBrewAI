package com.example.brainbrewai.data.repository

import com.example.brainbrewai.data.model.ChatMessage
import com.example.brainbrewai.data.remote.ChatRequest
import com.example.brainbrewai.data.remote.RetrofitInstance

class ChatRepository {

    suspend fun sendMessage(
        message: String
    ): String {

        return try {

            val response =
                RetrofitInstance.api.chatWithAI(
                    ChatRequest(message)
                )

            if (response.isSuccessful) {

                response.body()?.response
                    ?: "Empty response from AI"

            } else {

                "Server Error: ${response.code()}"
            }

        } catch (e: Exception) {

            "Error: ${e.message}"
        }
    }
}