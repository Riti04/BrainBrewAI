package com.example.brainbrewai.presentation.uploadnotes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.remote.RetrofitInstance
import com.example.brainbrewai.data.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadNotesViewModel : ViewModel() {

    private val historyRepository =
        HistoryRepository()

    private val _summary =
        MutableStateFlow("")

    val summary: StateFlow<String>
            = _summary

    private val _fileName =
        MutableStateFlow("")

    val fileName: StateFlow<String>
            = _fileName

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean>
            = _isLoading

    fun uploadPdf(
        file: MultipartBody.Part
    ) {

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val response =
                    RetrofitInstance.api
                        .uploadNotes(file)

                if (response.isSuccessful) {

                    val body = response.body()

                    _fileName.value =
                        body?.filename ?: ""

                    _summary.value =
                        body?.summary
                            ?: "No summary returned"

                    historyRepository.saveHistory(
                        title = _fileName.value,
                        content = _summary.value,
                        type = "summary"
                    )

                } else {

                    _summary.value =
                        "Upload failed: ${response.code()}"
                }

            } catch (e: Exception) {

                Log.e(
                    "UPLOAD_DEBUG",
                    e.message ?: ""
                )

                _summary.value =
                    "Error: ${e.localizedMessage}"

            } finally {

                _isLoading.value = false
            }
        }
    }
}