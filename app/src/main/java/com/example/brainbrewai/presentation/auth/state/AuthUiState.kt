package com.example.brainbrewai.presentation.auth.state

data class AuthUiState(

    val isLoading: Boolean = false,

    val isSuccess: Boolean = false,

    val error: String? = null
)