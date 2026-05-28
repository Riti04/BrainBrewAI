package com.example.brainbrewai.presentation.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.brainbrewai.core.components.GoogleSignInButton
import com.example.brainbrewai.data.auth.GoogleAuthClient
import com.example.brainbrewai.navigation.Screen
import com.example.brainbrewai.presentation.auth.viewmodel.AuthViewModel
import com.example.brainbrewai.ui.theme.CardRadius
import com.example.brainbrewai.ui.theme.InputRadius
import com.example.brainbrewai.ui.theme.PrimaryPurple
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun LoginScreen(
    navController: NavController
) {

    val viewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    val googleAuthClient = remember {
        GoogleAuthClient(context)
    }
    val uiState by viewModel.uiState.collectAsState()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }
    }
    val launcher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val task =
                    GoogleSignIn
                        .getSignedInAccountFromIntent(
                            result.data
                        )

                val account = task.result

                account?.idToken?.let {
                    viewModel.googleSignIn(it)
                }
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

            shape = CardRadius,

            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome Back",
                    style =
                        MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Text(
                    text = "Login to continue",
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )

                OutlinedTextField(
                    value = email,

                    onValueChange = {
                        email = it
                    },

                    label = {
                        Text("Email")
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape = InputRadius,

                    singleLine = true
                )

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

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
                        Modifier.fillMaxWidth(),

                    shape = InputRadius,

                    singleLine = true
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.End
                ) {

                    TextButton(
                        onClick = {
                            navController.navigate(
                                Screen.ForgotPassword.route
                            )
                        }
                    ) {

                        Text(
                            text = "Forgot Password?",
                            color = PrimaryPurple
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Button(
                    onClick = {
                        viewModel.login(
                            email = email,
                            password = password
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = InputRadius,

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                PrimaryPurple
                        )
                ) {

                    if (uiState.isLoading) {

                        CircularProgressIndicator(
                            color =
                                MaterialTheme.colorScheme.onPrimary
                        )

                    } else {

                        Text("Login")
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                GoogleSignInButton(
                    onClick = {
                        launcher.launch(
                            googleAuthClient.signInIntent()
                        )
                    }
                )

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                TextButton(
                    onClick = {
                        navController.navigate(
                            Screen.Register.route
                        )
                    }
                ) {

                    Text(
                        text =
                            "Don't have an account? Sign Up",
                        color = PrimaryPurple
                    )
                }

                uiState.error?.let { error ->

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )
                    Text(
                        text = error,
                        color =
                            MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}