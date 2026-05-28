package com.example.brainbrewai.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.brainbrewai.presentation.quiz.QuizQuestion

@Composable
fun QuizQuestionCard(
    question: QuizQuestion,
    onAnswerSelected: (String) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = question.question,
                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            question.options.forEach { option ->

                Row {

                    RadioButton(
                        selected =
                            question.selectedAnswer == option,

                        onClick = {
                            onAnswerSelected(option)
                        }
                    )

                    Text(
                        text = option
                    )
                }
            }
        }
    }
}