package com.example.brainbrewai.presentation.studyplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.data.remote.RetrofitInstance
import com.example.brainbrewai.data.remote.StudyPlannerRequest
import com.example.brainbrewai.data.repository.HistoryRepository
import com.example.brainbrewai.data.repository.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudyPlannerViewModel : ViewModel() {

    private val repository = StudyRepository()

    private val historyRepository =
        HistoryRepository()

    private val _studyPlan =
        MutableStateFlow("")

    val studyPlan: StateFlow<String>
            = _studyPlan

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean>
            = _isLoading

    init {
        loadSavedPlan()
    }

    fun generatePlan(
        goal: String,
        daysLeft: Int,
        dailyHours: Int
    ) {

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val response =
                    RetrofitInstance.api.generateStudyPlan(
                        StudyPlannerRequest(
                            goal = goal,
                            days_left = daysLeft,
                            daily_hours = dailyHours
                        )
                    )

                if (response.isSuccessful) {

                    _studyPlan.value =
                        response.body()?.plan
                            ?: "No study plan generated"

                    repository.saveStudyPlan(
                        goal = goal,
                        daysLeft = daysLeft,
                        dailyHours = dailyHours,
                        fullPlan = _studyPlan.value
                    )

                    historyRepository.saveHistory(
                        title = "$goal Study Plan",
                        content = _studyPlan.value,
                        type = "studyPlan"
                    )

                } else {

                    _studyPlan.value =
                        "Failed to generate study plan"
                }

            } catch (e: Exception) {

                _studyPlan.value =
                    e.localizedMessage
                        ?: "Something went wrong"

                e.printStackTrace()

            } finally {

                _isLoading.value = false
            }
        }
    }

    private fun loadSavedPlan() {

        viewModelScope.launch {

            try {

                _studyPlan.value =
                    repository.getStudyPlan()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun clearPlan() {
        _studyPlan.value = ""
    }
}