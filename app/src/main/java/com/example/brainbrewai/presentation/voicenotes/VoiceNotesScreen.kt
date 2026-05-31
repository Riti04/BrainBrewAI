package com.example.brainbrewai.presentation.voicenotes

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.brainbrewai.core.utils.PdfExporter
import com.example.brainbrewai.ui.theme.CardRadius
import com.example.brainbrewai.ui.theme.InputRadius
import com.example.brainbrewai.ui.theme.PrimaryPurple
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.example.brainbrewai.ui.theme.White
import java.util.Locale

@Composable
fun VoiceNotesScreen(
    navHostController: NavHostController,
    viewModel: VoiceNotesViewModel = viewModel()
) {

    val noteText by viewModel.noteText.collectAsState()
    val summary by viewModel.summary.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val spokenText =
                    result.data
                        ?.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS
                        )
                        ?.getOrNull(0)
                        ?: ""

                viewModel.updateText(spokenText)
            }
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGradient)
            .statusBarsPadding(),

        contentPadding = PaddingValues(20.dp),

        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item {

            Text(
                text = "Voice Notes",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        item {

            Text(
                text = "Speak your thoughts and turn them into AI notes instantly",
                color = White.copy(alpha = 0.9f)
            )
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = CardRadius
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text =
                            if (noteText.isBlank())
                                "🎤 Tap mic and start speaking..."
                            else noteText,

                        style =
                            MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        item {

            Button(
                onClick = {

                    val intent =
                        Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                        ).apply {

                            putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )

                            putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE,
                                Locale.getDefault()
                            )
                        }

                    launcher.launch(intent)
                },

                modifier = Modifier.fillMaxWidth(),

                shape = InputRadius,

                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPurple,
                    contentColor = White
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Mic"
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Start Recording")
            }
        }

        if (noteText.isNotBlank()) {

            item {

                Button(
                    onClick = {
                        viewModel.summarizeNote()
                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = InputRadius,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple,
                        contentColor = White
                    )
                ) {

                    Text(
                        "✨ Summarize with AI"
                    )
                }
            }
        }

        if (isLoading) {

            item {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = White
                    )
                }
            }
        }

        if (summary.isNotBlank()) {

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CardRadius
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "✨ AI Summary",
                            style =
                                MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = summary,
                            style =
                                MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            item {

                Button(
                    onClick = {

                        val fileName =
                            PdfExporter.exportToPdf(
                                context = context,
                                title = "BrainBrewAI Voice Notes",
                                content = summary
                            )

                        Toast.makeText(
                            context,
                            "Saved as $fileName",
                            Toast.LENGTH_LONG
                        ).show()
                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = InputRadius,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple,
                        contentColor = White
                    )
                ) {

                    Text(
                        "📄 Export Summary as PDF"
                    )
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(80.dp)
            )
        }
    }
}