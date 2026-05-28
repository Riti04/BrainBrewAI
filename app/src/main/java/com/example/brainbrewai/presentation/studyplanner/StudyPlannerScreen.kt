package com.example.brainbrewai.presentation.studyplanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.brainbrewai.core.component.AiAnswerCard
import com.example.brainbrewai.core.components.StudyPlannerInputCard
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.example.brainbrewai.ui.theme.White

@Composable
fun StudyPlannerScreen(
    navController: NavHostController,
    viewModel: StudyPlannerViewModel = viewModel()
) {

    var goal by remember { mutableStateOf("") }
    var daysLeft by remember { mutableStateOf("") }
    var dailyHours by remember { mutableStateOf("") }

    val studyPlan by viewModel.studyPlan.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
                text = "Study Planner",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        item {
            Text(
                text = "Generate your personalized AI study roadmap before your exam",
                style = MaterialTheme.typography.bodyMedium,
                color = White.copy(alpha = 0.9f)
            )
        }

        item {
            StudyPlannerInputCard(
                goal = goal,

                daysLeft = daysLeft,

                dailyHours = dailyHours,

                onGoalChange = {
                    goal = it
                },

                onDaysChange = {
                    daysLeft = it.filter(Char::isDigit)
                },

                onHoursChange = {
                    dailyHours = it.filter(Char::isDigit)
                },

                onGenerateClick = {

                    if (
                        goal.isNotBlank() &&
                        daysLeft.isNotBlank() &&
                        dailyHours.isNotBlank()
                    ) {

                        viewModel.generatePlan(
                            goal = goal,
                            daysLeft = daysLeft.toIntOrNull() ?: 0,
                            dailyHours = dailyHours.toIntOrNull() ?: 0
                        )
                    }
                }
            )
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = White
                    )
                }
            }
        }

        if (studyPlan.isNotBlank()) {
            item {
                AiAnswerCard(
                    title = "Your AI Study Plan",
                    answer = studyPlan
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}