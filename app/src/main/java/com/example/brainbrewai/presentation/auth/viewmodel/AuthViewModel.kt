package com.example.brainbrewai.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.auth.AuthRepository
import com.example.brainbrewai.presentation.auth.state.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _uiState =
        MutableStateFlow(AuthUiState())

    val uiState: StateFlow<AuthUiState>
            = _uiState

    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _uiState.value =
                AuthUiState(isLoading = true)

            val result =
                repository.login(email, password)

            _uiState.value =

                if (result.isSuccess) {

                    AuthUiState(
                        isSuccess = true
                    )

                } else {

                    AuthUiState(
                        error = result.exceptionOrNull()?.message
                    )
                }
        }
    }

    fun register(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _uiState.value =
                AuthUiState(isLoading = true)

            val result =
                repository.register(email, password)

            _uiState.value =

                if (result.isSuccess) {

                    AuthUiState(
                        isSuccess = true
                    )

                } else {

                    AuthUiState(
                        error = result.exceptionOrNull()?.message
                    )
                }
        }
    }
    fun resetPassword(
        email: String
    ) {

        viewModelScope.launch {

            _uiState.value =
                AuthUiState(isLoading = true)

            val result =
                repository.resetPassword(email)

            _uiState.value =

                if (result.isSuccess) {

                    AuthUiState(
                        isSuccess = true
                    )

                } else {

                    AuthUiState(
                        error =
                            result.exceptionOrNull()?.message
                    )
                }
        }
    }
    fun googleSignIn(
        idToken: String
    ) {

        viewModelScope.launch {

            _uiState.value =
                AuthUiState(
                    isLoading = true
                )

            try {

                val credential =
                    GoogleAuthProvider
                        .getCredential(
                            idToken,
                            null
                        )

                FirebaseAuth
                    .getInstance()
                    .signInWithCredential(
                        credential
                    )
                    .await()

                _uiState.value =
                    AuthUiState(
                        isSuccess = true
                    )

            } catch (e: Exception) {

                _uiState.value =
                    AuthUiState(
                        error =
                            e.message
                    )
            }
        }
    }
}
