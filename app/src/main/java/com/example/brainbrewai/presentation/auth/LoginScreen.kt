package com.example.brainbrewai.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.brainbrewai.navigation.Screen
import com.example.brainbrewai.presentation.auth.viewmodel.AuthViewModel
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.example.brainbrewai.core.components.GoogleSignInButton
import com.example.brainbrewai.data.auth.GoogleAuthClient
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun LoginScreen(
    navController: NavController
) {

    val viewModel: AuthViewModel =
        viewModel()
    val context = LocalContext.current

    val googleAuthClient =
        remember {
            GoogleAuthClient(context)
        }

    val uiState by
    viewModel.uiState.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(uiState.isSuccess) {

        if (uiState.isSuccess) {

            navController.navigate(
                Screen.Dashboard.route
            ) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts
                    .StartActivityForResult()
        ) { result ->

            if (result.resultCode ==
                Activity.RESULT_OK
            ) {

                val task =
                    GoogleSignIn
                        .getSignedInAccountFromIntent(
                            result.data
                        )

                val account =
                    task.result

                account?.idToken?.let {

                    viewModel.googleSignIn(it)
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
            text = "BrainBrew AI",
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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                viewModel.login(
                    email,
                    password
                )
            },
            modifier =
                Modifier.fillMaxWidth()
        ) {

            if (uiState.isLoading) {

                CircularProgressIndicator()

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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {

                viewModel.register(
                    email,
                    password
                )
            },
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Register")
        }

        uiState.error?.let { error ->

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            Text(
                text = error,
                color =
                    MaterialTheme.colorScheme.error
            )
        }
    }
}