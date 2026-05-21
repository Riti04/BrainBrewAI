package com.example.brainbrewai.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BrainBrewApi {

    @POST("chat")
    suspend fun askAi(
        @Body request: ChatRequest
    ): Response<ChatResponse>
}