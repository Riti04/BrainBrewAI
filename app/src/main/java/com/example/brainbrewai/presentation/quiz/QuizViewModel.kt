package com.example.brainbrewai.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.core.utils.QuizParser
import com.example.brainbrewai.data.remote.QuizRequest
import com.example.brainbrewai.data.remote.RetrofitInstance
import com.example.brainbrewai.data.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val historyRepository =
        HistoryRepository()

    private val _allQuizzes =
        MutableStateFlow<List<List<QuizQuestion>>>(emptyList())

    val allQuizzes: StateFlow<List<List<QuizQuestion>>>
            = _allQuizzes

    private val _selectedQuizIndex =
        MutableStateFlow(0)

    val selectedQuizIndex: StateFlow<Int>
            = _selectedQuizIndex

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean>
            = _isLoading

    private val _score =
        MutableStateFlow<Int?>(null)

    val score: StateFlow<Int?>
            = _score

    fun generateQuiz(topic: String) {

        if (topic.isBlank()) return

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val response =
                    RetrofitInstance.api.generateQuiz(
                        QuizRequest(topic)
                    )

                if (response.isSuccessful) {

                    val quizText =
                        response.body()?.quiz ?: ""

                    val parsedQuiz =
                        QuizParser.parseQuizText(
                            quizText
                        )

                    if (parsedQuiz.isNotEmpty()) {

                        val updatedList =
                            _allQuizzes.value +
                                    listOf(parsedQuiz)

                        _allQuizzes.value =
                            updatedList

                        _selectedQuizIndex.value =
                            updatedList.lastIndex

                        _score.value = null

                        historyRepository.saveHistory(
                            title = "$topic Quiz",
                            content = quizText,
                            type = "quiz"
                        )
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun selectQuiz(index: Int) {
        _selectedQuizIndex.value = index
        _score.value = null
    }

    fun selectAnswer(
        questionIndex: Int,
        answer: String
    ) {

        val quizIndex =
            _selectedQuizIndex.value

        val updatedQuizzes =
            _allQuizzes.value.toMutableList()

        val currentQuiz =
            updatedQuizzes.getOrNull(quizIndex)
                ?.toMutableList()
                ?: return

        currentQuiz[questionIndex] =
            currentQuiz[questionIndex].copy(
                selectedAnswer = answer
            )

        updatedQuizzes[quizIndex] =
            currentQuiz

        _allQuizzes.value =
            updatedQuizzes
    }

    fun submitQuiz() {

        val quiz =
            _allQuizzes.value
                .getOrNull(_selectedQuizIndex.value)
                ?: return

        _score.value =
            quiz.count {
                it.selectedAnswer == it.correctAnswer
            }
    }

    fun resetScore() {
        _score.value = null
    }
}