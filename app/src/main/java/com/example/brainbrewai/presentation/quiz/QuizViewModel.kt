package com.example.brainbrewai.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbrewai.core.utils.QuizParser
import com.example.brainbrewai.data.remote.QuizRequest
import com.example.brainbrewai.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _quizContent =
        MutableStateFlow(
            QuizContent()
        )

    val quizContent: StateFlow<QuizContent>
            = _quizContent

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean>
            = _isLoading

    private val _score =
        MutableStateFlow<Int?>(null)

    val score: StateFlow<Int?>
            = _score


    fun generateQuiz(
        topic: String,
        totalQuestions: Int
    ) {

        if (topic.isBlank()) return

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val response =
                    RetrofitInstance.api.generateQuiz(
                        QuizRequest(
                            topic = topic,
                            total_questions = totalQuestions
                        )
                    )

                if (response.isSuccessful) {

                    val quizText =
                        response.body()?.quiz ?: ""

                    _quizContent.value =
                        QuizParser.parseQuizText(
                            quizText
                        )

                    _score.value = null
                }

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                _isLoading.value = false
            }
        }
    }


    fun selectAnswer(
        questionIndex: Int,
        answer: String
    ) {

        val updatedMcqs =
            _quizContent.value.mcqs
                .toMutableList()

        if (
            questionIndex in updatedMcqs.indices
        ) {

            updatedMcqs[questionIndex] =
                updatedMcqs[questionIndex].copy(
                    selectedAnswer = answer
                )

            _quizContent.value =
                _quizContent.value.copy(
                    mcqs = updatedMcqs
                )
        }
    }


    fun submitQuiz() {

        val marks =
            _quizContent.value.mcqs.count { question ->

                val selectedOption =
                    question.selectedAnswer
                        ?.substringBefore(")")
                        ?.trim()

                selectedOption ==
                        question.correctAnswer
            }

        _score.value = marks
    }


    fun resetScore() {
        _score.value = null
    }
}