package com.example.brainbrewai.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbrewai.core.components.DashboardCard
import com.example.brainbrewai.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController
) {

    val dashboardItems = listOf(

        DashboardItem(
            title = "Upload Notes",
            description =
                "Upload PDF study materials"
        ),

        DashboardItem(
            title = "Ask AI",
            description =
                "Chat with your study assistant"
        ),

        DashboardItem(
            title = "Generate Quiz",
            description =
                "Create MCQs and interview questions"
        ),

        DashboardItem(
            title = "Study Planner",
            description =
                "Generate AI study roadmaps"
        ),

        DashboardItem(
            title = "Voice Notes",
            description =
                "Convert speech into study notes"
        ),

        DashboardItem(
            title = "History",
            description =
                "View previous AI sessions"
        )
    )

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text("BrainBrew AI")
                }
            )
        }
    ) { padding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding =
                PaddingValues(16.dp),
            horizontalArrangement =
                Arrangement.spacedBy(16.dp),
            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            items(dashboardItems) { item ->

                DashboardCard(
                    item = item,
                    onClick = {

                        when (item.title) {

                            "Ask AI" -> {

                                navController.navigate(
                                    Screen.Chat.route
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}