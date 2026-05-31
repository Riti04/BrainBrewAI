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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateQuizScreen(
    navController: NavHostController,
    viewModel: QuizViewModel = viewModel()
) {

    var topic by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val questionOptions =
        listOf(5, 10, 15, 20)

    var selectedQuestionCount by remember {
        mutableIntStateOf(10)
    }

    val quizContent by
    viewModel.quizContent.collectAsState()

    val isLoading by
    viewModel.isLoading.collectAsState()

    val score by
    viewModel.score.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGradient)
            .statusBarsPadding(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Text(
                text = "Generate Quiz",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        item {

            OutlinedTextField(
                value = topic,
                onValueChange = {
                    topic = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Enter topic")
                }
            )
        }

        item {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {

                OutlinedTextField(
                    value = "$selectedQuestionCount Questions",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    label = {
                        Text("Number of Questions")
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    questionOptions.forEach { count ->

                        DropdownMenuItem(
                            text = {
                                Text("$count Questions")
                            },
                            onClick = {

                                selectedQuestionCount = count
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        item {

            Button(
                onClick = {
                    viewModel.generateQuiz(
                        topic = topic,
                        totalQuestions = selectedQuestionCount
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPurple
                )
            ) {

                Text("Generate Quiz")
            }
        }

        if (isLoading) {

            item {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    CircularProgressIndicator(
                        color = White
                    )
                }
            }
        }

        if (quizContent.mcqs.isNotEmpty()) {

            item {

                Text(
                    text = "📝 MCQs",
                    style = MaterialTheme.typography.titleLarge,
                    color = White
                )
            }

            itemsIndexed(
                quizContent.mcqs
            ) { index, question ->

                QuizQuestionCard(
                    question = question,
                    onAnswerSelected = { answer ->

                        viewModel.selectAnswer(
                            index,
                            answer
                        )
                    }
                )
            }

            item {

                Button(
                    onClick = {
                        viewModel.submitQuiz()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple
                    )
                ) {

                    Text("Submit Quiz")
                }
            }
        }

        score?.let { marks ->

            item {

                Text(
                    text =
                        "Score: $marks / ${quizContent.mcqs.size}",
                    color = White,
                    style =
                        MaterialTheme.typography.titleLarge
                )
            }
        }

        if (quizContent.shortAnswers.isNotEmpty()) {

            item {

                Text(
                    text = "✍ Short Answer Questions",
                    style = MaterialTheme.typography.titleLarge,
                    color = White
                )
            }

            items(
                quizContent.shortAnswers.size
            ) { index ->

                val item =
                    quizContent.shortAnswers[index]

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = item.first,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = item.second
                        )
                    }
                }
            }
        }

        if (quizContent.interviewQuestions.isNotEmpty()) {

            item {

                Text(
                    text = "🎤 Interview Questions",
                    style = MaterialTheme.typography.titleLarge,
                    color = White
                )
            }

            items(
                quizContent.interviewQuestions.size
            ) { index ->

                val item =
                    quizContent.interviewQuestions[index]

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = item.first,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = item.second
                        )
                    }
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(80.dp)
            )
        }
    }
}