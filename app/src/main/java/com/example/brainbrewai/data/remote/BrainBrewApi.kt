package com.example.brainbrewai.data.remote

import com.example.brainbrewai.data.remote.ChatRequest
import com.example.brainbrewai.data.remote.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BrainBrewApi {

    @POST("chat")
    suspend fun chatWithAI(
        @Body request: ChatRequest
    ): Response<ChatResponse>
}