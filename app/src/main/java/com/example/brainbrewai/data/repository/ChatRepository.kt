package com.example.brainbrewai.data.repository

import com.example.brainbrewai.data.remote.ChatRequest
import com.example.brainbrewai.data.remote.RetrofitInstance

class ChatRepository {

    suspend fun askAI(
        message: String
    ): String {

        return try {

            val response =
                RetrofitInstance.api.askAi(
                    ChatRequest(message)
                )

            if (response.isSuccessful) {

                response.body()?.response
                    ?: "Empty AI response"

            } else {

                "Error: ${response.code()}"
            }

        } catch (e: Exception) {

            e.printStackTrace()

            "Error: ${e.message}"
        }
    }
}