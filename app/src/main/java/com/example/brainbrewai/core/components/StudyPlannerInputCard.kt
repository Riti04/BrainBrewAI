package com.example.brainbrewai.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.brainbrewai.ui.theme.CardRadius
import com.example.brainbrewai.ui.theme.InputRadius
import com.example.brainbrewai.ui.theme.PrimaryPurple
import com.example.brainbrewai.ui.theme.White

@Composable
fun StudyPlannerInputCard(
    goal: String,
    daysLeft: String,
    dailyHours: String,
    onGoalChange: (String) -> Unit,
    onDaysChange: (String) -> Unit,
    onHoursChange: (String) -> Unit,
    onGenerateClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CardRadius
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = goal,
                onValueChange = onGoalChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Goal (Java / DSA / DBMS)")
                },
                shape = InputRadius,
                singleLine = true
            )

            OutlinedTextField(
                value = daysLeft,
                onValueChange = onDaysChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Days left")
                },
                shape = InputRadius,
                singleLine = true
            )

            OutlinedTextField(
                value = dailyHours,
                onValueChange = onHoursChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Study hours per day")
                },
                shape = InputRadius,
                singleLine = true
            )

            Button(
                onClick = onGenerateClick,
                modifier = Modifier.fillMaxWidth(),
                shape = InputRadius,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPurple
                )
            ) {
                Text(
                    text = "Generate Plan",
                    color = White
                )
            }
        }
    }
}