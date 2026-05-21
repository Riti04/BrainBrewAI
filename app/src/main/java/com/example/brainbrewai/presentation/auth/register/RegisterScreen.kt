package com.example.brainbrewai.presentation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.brainbrewai.navigation.Screen
import com.example.brainbrewai.presentation.auth.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController
) {

    val viewModel: AuthViewModel =
        viewModel()

    val uiState by
    viewModel.uiState.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var localError by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(uiState.isSuccess) {

        if (uiState.isSuccess) {

            navController.navigate(
                Screen.Dashboard.route
            ) {

                popUpTo(Screen.Register.route) {
                    inclusive = true
                }
            }
        }
    }

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
            text = "Create Account",

            style =
                MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,

            onValueChange = {
                email = it
            },

            label = {
                Text("Email")
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,

            onValueChange = {
                password = it
            },

            label = {
                Text("Password")
            },

            visualTransformation =
                PasswordVisualTransformation(),

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,

            onValueChange = {
                confirmPassword = it
            },

            label = {
                Text("Confirm Password")
            },

            visualTransformation =
                PasswordVisualTransformation(),

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                when {

                    email.isBlank() -> {
                        localError =
                            "Email cannot be empty"
                    }

                    password.length < 6 -> {
                        localError =
                            "Password must be at least 6 characters"
                    }

                    password != confirmPassword -> {
                        localError =
                            "Passwords do not match"
                    }

                    else -> {

                        localError = null

                        viewModel.register(
                            email = email,
                            password = password
                        )
                    }
                }
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            if (uiState.isLoading) {

                CircularProgressIndicator()

            } else {

                Text("Create Account")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {

                navController.navigate(
                    Screen.Login.route
                )
            }
        ) {

            Text("Already have an account?")
        }

        localError?.let {

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text = it,

                color =
                    MaterialTheme.colorScheme.error
            )
        }

        uiState.error?.let {

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text = it,

                color =
                    MaterialTheme.colorScheme.error
            )
        }
    }
}