package com.example.brainbrewai.core.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.brainbrewai.ui.theme.CardRadius

@Composable
fun UpcomingRevisionCard(
    revisionText: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CardRadius
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "🔁 Upcoming Revision",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = if (revisionText.isBlank()) {
                    "No upcoming revision yet"
                } else {
                    revisionText
                }
            )
        }
    }
}