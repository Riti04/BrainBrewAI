package com.example.brainbrewai.presentation.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbrewai.core.components.DashboardCard
import com.example.brainbrewai.navigation.BottomNavBar
import com.example.brainbrewai.navigation.Screen
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.example.brainbrewai.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController
) {

    val user = FirebaseAuth
        .getInstance()
        .currentUser

    val userName =
        user?.displayName
            ?.split(" ")
            ?.firstOrNull()
            ?: "Friend"

    val dashboardItems = listOf(

        DashboardItem(
            title = "Upload Notes",
            description = "Upload PDF study materials"
        ),

        DashboardItem(
            title = "Ask AI",
            description = "Chat with your study assistant"
        ),

        DashboardItem(
            title = "Generate Quiz",
            description = "Create MCQs instantly"
        ),

        DashboardItem(
            title = "Study Planner",
            description = "Build smart AI roadmaps"
        ),

        DashboardItem(
            title = "Voice Notes",
            description = "Speech to notes"
        ),

        DashboardItem(
            title = "History",
            description = "View previous sessions"
        )
    )

    Scaffold(

        topBar = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PurpleGradient)
                    .statusBarsPadding()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )
            ) {

                Text(
                    text = "Welcome back, $userName 👋",
                    style =
                        MaterialTheme.typography.bodyLarge,
                    color =
                        White.copy(alpha = 0.9f)
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "BrainBrew AI",
                    style =
                        MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
        },

        bottomBar = {
            BottomNavBar(navController)
        },

        containerColor =
            MaterialTheme.colorScheme.background

    ) { paddingValues ->

        LazyVerticalGrid(

            columns = GridCells.Fixed(2),

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            contentPadding =
                PaddingValues(16.dp),

            horizontalArrangement =
                Arrangement.spacedBy(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {

            itemsIndexed(
                dashboardItems
            ) { index, item ->

                val scale by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis =
                            350 + index * 120
                    ),
                    label = ""
                )

                Box(
                    modifier =
                        Modifier.scale(scale)
                ) {

                    DashboardCard(
                        item = item,

                        onClick = {

                            when (item.title) {

                                "Upload Notes" -> {
                                    navController.navigate(
                                        Screen.UploadNotes.route
                                    )
                                }

                                "Ask AI" -> {
                                    navController.navigate(
                                        Screen.Chat.route
                                    )
                                }

                                "History" -> {
                                    navController.navigate(
                                        Screen.Study.route
                                    )
                                }

                                "Generate Quiz" -> {
                                    navController.navigate(
                                        Screen.GenerateQuiz.route
                                    )
                                }

                                "Study Planner" -> {
                                    navController.navigate(
                                        Screen.StudyPlanner.route
                                    )
                                }

                                "Voice Notes" -> {
                                    navController.navigate(
                                        Screen.VoiceNotes.route
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}