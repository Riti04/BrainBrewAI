package com.example.brainbrewai.core.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.brainbrewai.presentation.dashboard.DashboardItem
import com.example.brainbrewai.ui.theme.CardRadius
import com.example.brainbrewai.ui.theme.PrimaryPurple
import com.example.brainbrewai.ui.theme.White

@Composable
fun DashboardCard(
    item: DashboardItem,
    onClick: () -> Unit
) {

    var pressed by remember {
        mutableStateOf(false)
    }

    val scale by animateFloatAsState(
        targetValue =
            if (pressed) 0.96f else 1f,
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .scale(scale)
            .clickable(
                onClick = {

                    pressed = true
                    onClick()
                    pressed = false
                }
            ),

        shape = CardRadius,

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor = White
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),

            verticalArrangement =
                Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector =
                    getDashboardIcon(item.title),

                contentDescription = null,

                tint = PrimaryPurple,

                modifier =
                    Modifier.size(34.dp)
            )

            Column {

                Text(
                    text = item.title,

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )

                Text(
                    text =
                        item.description,

                    style =
                        MaterialTheme.typography.bodySmall,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun getDashboardIcon(
    title: String
): ImageVector {

    return when (title) {

        "Upload Notes" ->
            Icons.Default.Description

        "Ask AI" ->
            Icons.Default.Psychology

        "Generate Quiz" ->
            Icons.Default.Quiz

        "Study Planner" ->
            Icons.Default.School

        "Voice Notes" ->
            Icons.Default.Mic

        "History" ->
            Icons.Default.History

        else ->
            Icons.Default.AutoAwesome
    }
}