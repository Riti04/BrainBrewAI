package com.example.brainbrewai.presentation.auth.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbrewai.navigation.Screen
import com.example.brainbrewai.ui.theme.*

@Composable
fun WelcomeScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGradient)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            shape = CardRadius
        ) {

            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(72.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "BrainBrew AI",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Your AI-powered study companion"
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        navController.navigate(Screen.Login.route)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = InputRadius,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple
                    )
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.Register.route)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = InputRadius
                ) {
                    Text(
                        "Create Account",
                        color = PrimaryPurple
                    )
                }
            }
        }
    }
}