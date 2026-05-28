package com.example.brainbrewai.presentation.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbrewai.navigation.Screen

@Composable
fun OnboardingScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6C63FF),
                        Color(0xFF8E7CFF),
                        Color.White
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "BrainBrew AI",

                style = MaterialTheme.typography.displaySmall,

                color = Color.White,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Study Smarter with AI",

                style = MaterialTheme.typography.headlineSmall,

                color = Color.White,

                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Summaries, quizzes, planners and AI tutoring in one powerful app.",

                textAlign = TextAlign.Center,

                color = Color.White.copy(alpha = 0.9f),

                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Welcome.route)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {

                Text(
                    text = "Get Started",

                    color = Color(0xFF6C63FF),

                    style = MaterialTheme.typography.titleMedium,

                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}