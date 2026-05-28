package com.example.brainbrewai.core.utils

import com.example.brainbrewai.presentation.quiz.QuizQuestion

object QuizParser {

    fun parseQuizText(text: String): List<QuizQuestion> {

        val questions = mutableListOf<QuizQuestion>()

        val blocks =
            text.split(Regex("""\n\d+\.\s+\*\*"""))
                .drop(1)

        blocks.forEach { block ->

            try {

                val lines =
                    block.lines()
                        .map { it.trim() }
                        .filter { it.isNotBlank() }

                if (lines.size < 6) return@forEach

                val question =
                    lines[0]
                        .replace("**", "")
                        .trim()

                val options =
                    lines.filter {
                        it.startsWith("A)") ||
                                it.startsWith("B)") ||
                                it.startsWith("C)") ||
                                it.startsWith("D)")
                    }

                val answerLine =
                    lines.find {
                        it.startsWith("Answer:")
                    } ?: ""

                val correctAnswer =
                    answerLine
                        .replace("Answer:", "")
                        .trim()

                if (
                    question.isNotEmpty() &&
                    options.size >= 4
                ) {

                    questions.add(
                        QuizQuestion(
                            question = question,
                            options = options,
                            correctAnswer = correctAnswer,
                            selectedAnswer = null
                        )
                    )
                }

            } catch (
                e: Exception
            ) {
                e.printStackTrace()
            }
        }

        return questions
    }
}