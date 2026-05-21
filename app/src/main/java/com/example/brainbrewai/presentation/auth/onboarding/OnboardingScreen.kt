package com.example.brainbrewai.presentation.auth.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbrewai.navigation.Screen

@Composable
fun OnboardingScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement =
            Arrangement.Center,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text =
                "Study Smarter with AI",

            style =
                MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text =
                "Summaries, quizzes, planners and AI tutoring in one app."
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {

                navController.navigate(
                    Screen.Welcome.route
                )
            }
        ) {

            Text("Get Started")
        }
    }
}