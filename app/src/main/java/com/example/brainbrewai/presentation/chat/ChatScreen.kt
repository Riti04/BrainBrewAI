package com.example.brainbrewai.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brainbrewai.data.model.ChatMessage

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {

    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(messages) { message ->

                ChatBubble(message)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Ask AI...")
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {

                    viewModel.sendMessage(text)
                    text = ""
                }
            ) {
                Text("Send")
            }
        }

        if (isLoading) {

            Spacer(modifier = Modifier.height(8.dp))

            CircularProgressIndicator()
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment =
            if (message.isUser)
                Alignment.CenterEnd
            else
                Alignment.CenterStart
    ) {

        Surface(
            color =
                if (message.isUser)
                    MaterialTheme.colorScheme.primary
                else
                    Color.DarkGray,

            shape = MaterialTheme.shapes.medium
        ) {

            Text(
                text = message.message,
                color = Color.White,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}