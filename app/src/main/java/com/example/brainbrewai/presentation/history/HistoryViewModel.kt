package com.example.brainbrewai.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repo: HistoryRepository =
        HistoryRepository()
) : ViewModel() {

    private val _list =
        MutableStateFlow<List<Map<String, Any>>>(emptyList())

    val list =
        _list.asStateFlow()

    init {
        loadList()
    }

    fun loadList() {

        viewModelScope.launch {

            _list.value =
                try {
                    repo.getHistoryList()
                } catch (e: Exception) {
                    emptyList()
                }
        }
    }
}