package com.example.brainbrewai.presentation.voicenotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.remote.ChatRequest
import com.example.brainbrewai.data.remote.RetrofitInstance
import com.example.brainbrewai.data.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VoiceNotesViewModel : ViewModel() {

    private val repository =
        HistoryRepository()

    private val _noteText =
        MutableStateFlow("")
    val noteText: StateFlow<String>
            = _noteText

    private val _summary =
        MutableStateFlow("")
    val summary: StateFlow<String>
            = _summary

    private val _isLoading =
        MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
            = _isLoading


    fun updateText(
        text: String
    ) {

        _noteText.value = text
    }


    fun summarizeNote() {

        if (_noteText.value.isBlank()) return

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val response =
                    RetrofitInstance.api.chatWithAI(
                        ChatRequest(
                            message =
                                "Summarize these notes clearly:\n${_noteText.value}"
                        )
                    )

                if (response.isSuccessful) {

                    val aiSummary =
                        response.body()?.response
                            ?: "No summary generated"

                    _summary.value =
                        aiSummary

                    repository.saveHistory(
                        title = "Voice Note",
                        content = aiSummary,
                        type = "voiceNote"
                    )
                }

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                _isLoading.value = false
            }
        }
    }
}