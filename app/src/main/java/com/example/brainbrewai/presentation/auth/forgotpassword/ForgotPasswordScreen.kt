package com.example.brainbrewai.presentation.auth.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.brainbrewai.presentation.auth.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavController
) {

    val viewModel: AuthViewModel =
        viewModel()

    val uiState by
    viewModel.uiState.collectAsState()

    var email by remember {
        mutableStateOf("")
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
            text = "Reset Password",

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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                viewModel.resetPassword(email)
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            if (uiState.isLoading) {

                CircularProgressIndicator()

            } else {

                Text("Send Reset Link")
            }
        }

        uiState.isSuccess.let {

            if (it) {

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Text(
                    text =
                        "Password reset email sent"
                )
            }
        }

        uiState.error?.let {

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Text(
                text = it,

                color =
                    MaterialTheme.colorScheme.error
            )
        }
    }
}