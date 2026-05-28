package com.example.brainbrewai.core.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.example.brainbrewai.ui.theme.PrimaryPurple

@Composable
fun StyledAiText(
    text: String
) {
    val titleSize =
        MaterialTheme.typography.titleMedium.fontSize

    val styledText =
        buildAnnotatedString {
            parseStyledText(
                text = text,
                titleFontSize = titleSize
            )
        }

    Text(
        text = styledText,
        style =
            MaterialTheme.typography.bodyLarge
    )
}

private fun AnnotatedString.Builder.parseStyledText(
    text: String,
    titleFontSize: androidx.compose.ui.unit.TextUnit
) {

    text.lines().forEach { line ->

        when {

            line.startsWith("Day") ||
                    line.startsWith("Week") ||
                    line.startsWith("Chapter") -> {

                pushStyle(
                    SpanStyle(
                        color = PrimaryPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = titleFontSize
                    )
                )

                append(line)

                pop()

                append("\n\n")
            }

            line.startsWith("-") -> {

                append("• ")
                append(
                    line.removePrefix("-").trim()
                )
                append("\n")
            }

            else -> {
                append(line)
                append("\n")
            }
        }
    }
}