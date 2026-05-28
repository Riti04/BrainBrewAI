package com.example.brainbrewai.presentation.study

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.brainbrewai.core.component.ActiveStudyPlanCard
import com.example.brainbrewai.core.component.StudyProgressCard
import com.example.brainbrewai.core.component.TodaysGoalCard
import com.example.brainbrewai.core.component.UpcomingRevisionCard
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.example.brainbrewai.ui.theme.White

@Composable
fun StudyScreen(
    navController: NavController,
    viewModel: StudyViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWorkspace()
    }

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
                text = "Study Workspace",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        item {
            ActiveStudyPlanCard(
                plan = uiState.activePlan
            )
        }

        item {
            TodaysGoalCard(
                goalText = uiState.todayGoal
            )
        }

        item {
            StudyProgressCard(
                completedDays = uiState.completedDays,
                totalDays = uiState.totalDays
            )
        }

        item {
            UpcomingRevisionCard(
                revisionText = uiState.upcomingRevision
            )
        }
    }
}