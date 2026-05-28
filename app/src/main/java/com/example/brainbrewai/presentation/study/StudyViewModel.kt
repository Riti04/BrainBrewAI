package com.example.brainbrewai.presentation.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.repository.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudyViewModel : ViewModel() {

    private val repository = StudyRepository()

    private val _uiState =
        MutableStateFlow(StudyUiState())

    val uiState: StateFlow<StudyUiState>
            = _uiState

    init {
        loadWorkspace()
    }

    fun loadWorkspace() {

        viewModelScope.launch {

            try {

                val data =
                    repository.getStudyWorkspace()

                if (data != null) {

                    val goal =
                        data["goal"] as? String ?: ""

                    val daysLeft =
                        (data["daysLeft"] as? Long)?.toInt() ?: 0

                    val dailyHours =
                        (data["dailyHours"] as? Long)?.toInt() ?: 0

                    val todayGoal =
                        data["todayGoal"] as? String ?: ""

                    val upcomingRevision =
                        data["upcomingRevision"] as? String ?: ""

                    val completedDays =
                        (data["completedDays"] as? Long)?.toInt() ?: 0

                    val totalDays =
                        (data["totalDays"] as? Long)?.toInt() ?: 0


                    _uiState.value =
                        StudyUiState(
                            activePlan =
                                "$goal • $daysLeft days • $dailyHours hrs/day",

                            todayGoal = todayGoal,

                            upcomingRevision = upcomingRevision,

                            completedDays = completedDays,

                            totalDays = totalDays
                        )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}