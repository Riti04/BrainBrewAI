package com.example.brainbrewai.core.utils

import com.example.brainbrewai.presentation.quiz.QuizContent
import com.example.brainbrewai.presentation.quiz.QuizQuestion

object QuizParser {

    fun parseQuizText(
        text: String
    ): QuizContent {

        val mcqs =
            mutableListOf<QuizQuestion>()

        val shortAnswers =
            mutableListOf<Pair<String, String>>()

        val interviewQuestions =
            mutableListOf<Pair<String, String>>()

        val mcqSection =
            text.substringAfter("### MCQ", "")
                .substringBefore("### Short Answer")

        val shortSection =
            text.substringAfter("### Short Answer", "")
                .substringBefore("### Interview Questions")

        val interviewSection =
            text.substringAfter("### Interview Questions", "")

        parseMcqs(
            mcqSection,
            mcqs
        )

        parseQaSection(
            shortSection,
            shortAnswers
        )

        parseQaSection(
            interviewSection,
            interviewQuestions
        )

        return QuizContent(
            mcqs = mcqs,
            shortAnswers = shortAnswers,
            interviewQuestions = interviewQuestions
        )
    }

    private fun parseMcqs(
        section: String,
        result: MutableList<QuizQuestion>
    ) {

        val blocks =
            section.split(
                Regex("""(?=\n\d+\.)""")
            )

        blocks.forEach { block ->

            val lines =
                block.lines()
                    .map { it.trim() }
                    .filter { it.isNotBlank() }

            if (lines.isEmpty()) return@forEach

            val question =
                lines.first()
                    .replace(
                        Regex("""^\d+\.\s*"""),
                        ""
                    )
                    .replace("**", "")
                    .trim()

            val options =
                lines.filter {
                    it.startsWith("A)") ||
                            it.startsWith("B)") ||
                            it.startsWith("C)") ||
                            it.startsWith("D)")
                }

            val correctAnswer =
                lines.find {
                    it.startsWith("Answer:")
                }
                    ?.replace("Answer:", "")
                    ?.trim()
                    ?.substringBefore(" ")
                    ?.substringBefore(")")
                    ?: ""

            val explanation =
                lines.find {
                    it.startsWith("Explanation:")
                }
                    ?.replace("Explanation:", "")
                    ?.trim()
                    ?: ""

            if (options.size == 4) {

                result.add(
                    QuizQuestion(
                        question = question,
                        options = options,
                        correctAnswer = correctAnswer,
                        explanation = explanation,
                        selectedAnswer = null
                    )
                )
            }
        }
    }

    private fun parseQaSection(
        section: String,
        result: MutableList<Pair<String, String>>
    ) {

        val blocks =
            section.split(
                Regex("""(?=\n\d+\.)""")
            )

        blocks.forEach { block ->

            val lines =
                block.lines()
                    .map { it.trim() }
                    .filter { it.isNotBlank() }

            if (lines.isEmpty()) return@forEach

            val question =
                lines.first()
                    .replace(
                        Regex("""^\d+\.\s*"""),
                        ""
                    )
                    .replace("**", "")
                    .trim()

            val answer =
                lines.drop(1)
                    .joinToString("\n")

            if (question.isNotBlank()) {

                result.add(
                    question to answer
                )
            }
        }
    }
}