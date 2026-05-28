package com.example.brainbrewai.core.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.brainbrewai.core.component.StyledAiText
import com.example.brainbrewai.ui.theme.CardRadius
import com.example.brainbrewai.ui.theme.PrimaryPurple

@Composable
fun SummaryCard(
    fileName: String,
    summary: String,
    clipboardManager: ClipboardManager,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Card(
        modifier = modifier.fillMaxWidth(),

        shape = CardRadius,

        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = fileName,
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )

                IconButton(
                    onClick = {

                        clipboardManager.setText(
                            AnnotatedString(summary)
                        )

                        Toast.makeText(
                            context,
                            "Summary copied",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.ContentCopy,

                        contentDescription =
                            "Copy Summary",

                        tint = PrimaryPurple
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            StyledAiText(
                text = summary,
            )
        }
    }
}