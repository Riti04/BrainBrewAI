package com.example.brainbrewai.core.component

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
fun ActiveStudyPlanCard(
    plan: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CardRadius
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "📚 Your Study Plan",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = if (plan.isBlank()) {
                    "No study plan generated yet"
                } else {
                    plan
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}