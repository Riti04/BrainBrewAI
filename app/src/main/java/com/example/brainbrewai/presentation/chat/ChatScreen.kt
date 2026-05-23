package com.example.brainbrewai.presentation.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.brainbrewai.data.model.ChatMessage

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

    LaunchedEffect(messages.size) {

        if (messages.isNotEmpty()) {

            listState.animateScrollToItem(
                messages.lastIndex
            )
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "BrainBrew AI",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Smart Study Assistant",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF111827),
                    titleContentColor = Color.White
                )
            )
        },

        bottomBar = {

            Surface(
                tonalElevation = 8.dp
            ) {

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(

                        value = messageText,

                        onValueChange = {
                            messageText = it
                        },

                        modifier = Modifier.weight(1f),

                        placeholder = {
                            Text("Ask anything...")
                        },

                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(

                        modifier = Modifier
                            .size(55.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFF6366F1),
                                        Color(0xFF8B5CF6)
                                    )
                                )
                            ),

                        contentAlignment = Alignment.Center
                    ) {

                        IconButton(

                            onClick = {

                                if (messageText.isNotBlank()) {

                                    viewModel.sendMessage(
                                        messageText
                                    )

                                    messageText = ""
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF9FAFB))
        ) {

            LazyColumn(

                state = listState,

                modifier = Modifier.weight(1f),

                verticalArrangement = Arrangement.spacedBy(12.dp),

                contentPadding = PaddingValues(12.dp)
            ) {

                items(messages) { message ->

                    MessageBubble(message)
                }

                item {

                    AnimatedVisibility(
                        visible = isLoading
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Card {

                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        strokeWidth = 2.dp
                                    )

                                    Spacer(
                                        modifier = Modifier.width(10.dp)
                                    )

                                    Text(
                                        text = "Thinking..."
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: ChatMessage
) {

    val alignment =
        if (message.isUser)
            Arrangement.End
        else
            Arrangement.Start

    val bubbleColor =
        if (message.isUser)
            Brush.linearGradient(
                listOf(
                    Color(0xFF6366F1),
                    Color(0xFF8B5CF6)
                )
            )
        else
            Brush.linearGradient(
                listOf(
                    Color.White,
                    Color.White
                )
            )

    val textColor =
        if (message.isUser)
            Color.White
        else
            Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = alignment
    ) {

        Card(

            modifier = Modifier.fillMaxWidth(0.82f),

            shape = RoundedCornerShape(
                topStart = if (message.isUser) 16.dp else 0.dp,
                topEnd = if (message.isUser) 0.dp else 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        ) {

            Box(

                modifier = Modifier
                    .background(bubbleColor)
                    .padding(14.dp)
            ) {

                Text(
                    text = message.text,
                    color = textColor
                )
            }
        }
    }
}