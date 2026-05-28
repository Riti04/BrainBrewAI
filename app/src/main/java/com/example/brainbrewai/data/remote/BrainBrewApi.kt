package com.example.brainbrewai.data.remote

import com.example.brainbrewai.data.remote.ChatRequest
import com.example.brainbrewai.data.remote.ChatResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface BrainBrewApi {

    @POST("chat")
    suspend fun chatWithAI(
        @Body request: ChatRequest
    ): Response<ChatResponse>

    @Multipart
    @POST("upload-pdf")
    suspend fun uploadNotes(
        @Part file: MultipartBody.Part
    ): Response<UploadNotesResponse>

    @POST("quiz")
    suspend fun generateQuiz(
        @Body request: QuizRequest
    ): Response<QuizResponse>

    @POST("planner")
    suspend fun generateStudyPlan(
        @Body request: StudyPlannerRequest
    ): Response<StudyPlannerResponse>
}