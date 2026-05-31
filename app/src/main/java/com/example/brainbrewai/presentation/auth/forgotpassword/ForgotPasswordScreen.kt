package com.example.brainbrewai.presentation.auth.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    val uiState by
    viewModel.uiState.collectAsState()

    val context =
        LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    LaunchedEffect(
        uiState.isSuccess,
        uiState.error
    ) {

        if (uiState.isSuccess) {

            Toast.makeText(
                context,
                "Reset link sent to your email",
                Toast.LENGTH_LONG
            ).show()

            navController.popBackStack()
        }

        uiState.error?.let {

            Toast.makeText(
                context,
                it,
                Toast.LENGTH_LONG
            ).show()
        }
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
                    text = "Forgot Password?",
                    style =
                        MaterialTheme.typography.headlineMedium
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

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
                            imageVector =
                                Icons.Default.Email,
                            contentDescription = null,
                            tint = PrimaryPurple
                        )
                    },

                    shape = InputRadius,

                    modifier =
                        Modifier.fillMaxWidth(),

                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(
                    onClick = {
                        viewModel.resetPassword(
                            email
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape = InputRadius,

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                PrimaryPurple
                        )
                ) {

                    if (uiState.isLoading) {

                        CircularProgressIndicator(
                            color = White,
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            "Send Reset Link"
                        )
                    }
                }
            }
        }
    }
}