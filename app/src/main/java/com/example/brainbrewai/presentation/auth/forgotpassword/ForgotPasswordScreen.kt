package com.example.brainbrewai.presentation.auth.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.brainbrewai.presentation.auth.viewmodel.AuthViewModel
import com.example.brainbrewai.ui.theme.*

@Composable
fun ForgotPasswordScreen(
    navController: NavController
) {

    val viewModel: AuthViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

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
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    "Forgot Password?",
                    style = MaterialTheme.typography.headlineMedium
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
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            null,
                            tint = PrimaryPurple
                        )
                    },
                    shape = InputRadius,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.resetPassword(email)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = InputRadius,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple
                    )
                ) {
                    Text("Send Reset Link")
                }
            }
        }
    }
}