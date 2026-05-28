package com.example.brainbrewai.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brainbrewai.core.components.HistoryCard
import com.example.brainbrewai.ui.history.HistoryViewModel
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.example.brainbrewai.ui.theme.White

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {

    val history by
    viewModel.list.collectAsState()

    val filteredHistory =
        history?.filter { item ->
            (item["type"] as? String) != "studyPlan"
        } ?: emptyList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGradient)
            .statusBarsPadding(),

        contentPadding = PaddingValues(20.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "History",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        items(filteredHistory) { item ->

            HistoryCard(
                title = item["title"] as? String ?: "",
                content = item["content"] as? String
                    ?: item["summary"] as? String
                    ?: "",
                type = item["type"] as? String ?: ""
            )
        }
    }
}