package com.example.brainbrewai.presentation.auth.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbrewai.core.components.AuthCard
import com.example.brainbrewai.core.components.GradientBackground
import com.example.brainbrewai.navigation.Screen

@Composable
fun WelcomeScreen(
    navController: NavController
) {

    GradientBackground {

        Column(
            modifier = Modifier.fillMaxSize(),

            verticalArrangement =
                Arrangement.Center,

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector =
                    Icons.Default.AutoAwesome,

                contentDescription = null,

                tint =
                    MaterialTheme.colorScheme.primary,

                modifier =
                    Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "BrainBrew AI",

                style =
                    MaterialTheme.typography.headlineLarge,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    "Your Multi-Agent Study Assistant",

                style =
                    MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(40.dp))

            AuthCard {

                Button(
                    onClick = {

                        navController.navigate(
                            Screen.Login.route
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text("Login")
                }

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                OutlinedButton(
                    onClick = {

                        navController.navigate(
                            Screen.Register.route
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text("Create Account")
                }
            }
        }
    }
}