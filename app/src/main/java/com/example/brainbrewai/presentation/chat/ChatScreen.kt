package com.example.brainbrewai.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brainbrewai.data.model.ChatMessage
import com.example.brainbrewai.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {

    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var messageText by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("BrainBrew AI")
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = PrimaryPurple,
                        titleContentColor = White
                    )
            )
        },

        bottomBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = messageText,

                    onValueChange = {
                        messageText = it
                    },

                    placeholder = {
                        Text("Ask anything...")
                    },

                    modifier = Modifier.weight(1f),

                    shape = InputRadius
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Button(
                    onClick = {

                        if (messageText.isNotBlank()) {

                            viewModel.sendMessage(
                                messageText
                            )

                            messageText = ""
                        }
                    },

                    shape = InputRadius,

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                PrimaryPurple
                        )
                ) {

                    Icon(
                        Icons.Default.Send,
                        contentDescription = null
                    )
                }
            }
        }

    ) { padding ->

        LazyColumn(
            state = listState,

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(White),

            contentPadding =
                PaddingValues(12.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            items(messages) { message ->

                MessageBubble(message)
            }

            if (isLoading) {

                item {

                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: ChatMessage
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement =
            if (message.isUser)
                Arrangement.End
            else
                Arrangement.Start
    ) {

        Surface(
            color =
                if (message.isUser)
                    PrimaryPurple
                else
                    MaterialTheme.colorScheme.surfaceVariant,

            shape = CardRadius
        ) {

            Text(
                text = message.text,

                modifier = Modifier.padding(14.dp),

                color =
                    if (message.isUser)
                        White
                    else
                        MaterialTheme.colorScheme.onSurface
            )
        }
    }
}