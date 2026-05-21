package com.example.brainbrewai.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {

    OutlinedButton(
        onClick = onClick,

        modifier =
            Modifier.fillMaxWidth()
    ) {

        Text("Continue with Google")
    }
}