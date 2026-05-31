package com.example.brainbrewai.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.brainbrewai.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    LaunchedEffect(Unit) {

        delay(2000)

        val currentUser =
            FirebaseAuth
                .getInstance()
                .currentUser

        if (currentUser != null) {

            // already logged in
            navController.navigate(
                Screen.Dashboard.route
            ) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }

        } else {

            // first time user
            navController.navigate(
                Screen.Welcome.route
            ) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "BrainBrew AI",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Multi-Agent Study Assistant",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}