package com.example.brainbrewai.core.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.brainbrewai.ui.theme.CardRadius
import com.example.brainbrewai.ui.theme.PrimaryPurple

@Composable
fun StudyProgressCard(
    completedDays: Int,
    totalDays: Int
) {

    val progress =
        if (totalDays > 0) {
            completedDays.toFloat() / totalDays.toFloat()
        } else {
            0f
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CardRadius
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "📈 Progress",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryPurple
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "$completedDays / $totalDays days completed",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}