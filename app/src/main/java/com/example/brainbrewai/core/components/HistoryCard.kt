package com.example.brainbrewai.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.brainbrewai.ui.theme.CardRadius

@Composable
fun HistoryCard(
    title: String,
    content: String,
    type: String
) {

    val typeLabel = when (type) {
        "askAi" -> "🤖 Ask AI"
        "summary" -> "📄 Upload Notes"
        "quiz" -> "📝 Quiz"
        "studyPlan" -> "📚 Study Plan"
        "voiceNote" -> "🎤 Voice Note"
        else -> "📌 History"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CardRadius
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = typeLabel,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = content.take(180)
            )
        }
    }
}