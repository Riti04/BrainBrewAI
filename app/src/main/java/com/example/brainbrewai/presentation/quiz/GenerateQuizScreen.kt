package com.example.brainbrewai.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.brainbrewai.core.components.QuizQuestionCard
import com.example.brainbrewai.ui.theme.PrimaryPurple
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.example.brainbrewai.ui.theme.White

@Composable
fun GenerateQuizScreen(
    navController: NavHostController,
    viewModel: QuizViewModel = viewModel()
) {

    var topic by remember {
        mutableStateOf("")
    }

    val allQuizzes by
    viewModel.allQuizzes.collectAsState()

    val selectedQuizIndex by
    viewModel.selectedQuizIndex.collectAsState()

    val isLoading by
    viewModel.isLoading.collectAsState()

    val score by
    viewModel.score.collectAsState()

    val currentQuiz =
        allQuizzes.getOrNull(
            selectedQuizIndex
        ) ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGradient)
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        Text(
            text = "Generate Quiz",
            style =
                MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = White
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = topic,
            onValueChange = {
                topic = it
            },
            modifier =
                Modifier.fillMaxWidth(),
            label = {
                Text("Enter topic")
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                if (topic.isNotBlank()) {
                    viewModel.generateQuiz(topic)
                }
            },
            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        PrimaryPurple
                )
        ) {
            Text("Generate Quiz")
        }

        if (allQuizzes.isNotEmpty()) {

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            ScrollableTabRow(
                selectedTabIndex =
                    selectedQuizIndex
            ) {

                allQuizzes.forEachIndexed { index, _ ->

                    Tab(
                        selected =
                            selectedQuizIndex == index,

                        onClick = {
                            viewModel.selectQuiz(
                                index
                            )
                        },

                        text = {
                            Text(
                                "Quiz ${index + 1}"
                            )
                        }
                    )
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(
                color = White
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            itemsIndexed(
                currentQuiz
            ) { index, question ->

                QuizQuestionCard(
                    question = question,

                    onAnswerSelected = {
                        viewModel.selectAnswer(
                            index,
                            it
                        )
                    }
                )

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
            }
        }

        if (currentQuiz.isNotEmpty()) {

            Button(
                onClick = {
                    viewModel.submitQuiz()
                },

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            PrimaryPurple
                    )
            ) {
                Text("Submit Quiz")
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    viewModel.generateQuiz(topic)
                },

                modifier =
                    Modifier.fillMaxWidth()
            ) {
                Text("Generate Another Quiz")
            }
        }

        score?.let {

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Text(
                text =
                    "Score: $it / ${currentQuiz.size}",
                color = White,
                style =
                    MaterialTheme.typography.titleLarge
            )
        }
    }
}