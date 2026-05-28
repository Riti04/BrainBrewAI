package com.example.brainbrewai.presentation.uploadnotes

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.brainbrewai.ui.theme.CardRadius
import com.example.brainbrewai.ui.theme.InputRadius
import com.example.brainbrewai.ui.theme.PrimaryPurple
import com.example.brainbrewai.ui.theme.PurpleGradient
import com.example.brainbrewai.ui.theme.White
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import androidx.compose.ui.platform.LocalClipboardManager
import com.example.brainbrewai.core.components.SummaryCard

@Composable
fun UploadNotesScreen(
    navController: NavHostController,
    viewModel: UploadNotesViewModel = viewModel()
) {
    val clipboardManager =
        LocalClipboardManager.current
    val context = LocalContext.current

    val summary by viewModel.summary.collectAsState()
    val fileName by viewModel.fileName.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            selectedUri = uri
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGradient)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Text(
                text = "Upload Notes",
                style =
                    MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "Upload your PDF and let BrainBrew AI summarize it for you",
                color =
                    White.copy(alpha = 0.9f)
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape = CardRadius,

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surface
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.UploadFile,

                        contentDescription = null,

                        tint = PrimaryPurple,

                        modifier =
                            Modifier.size(60.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Button(
                        onClick = {
                            launcher.launch(
                                "application/pdf"
                            )
                        },

                        shape = InputRadius,

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    PrimaryPurple
                            )
                    ) {

                        Text("Choose PDF")
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    selectedUri?.let { uri ->

                        Text(
                            text =
                                getFileName(
                                    context,
                                    uri
                                ),

                            style =
                                MaterialTheme.typography.bodyMedium
                        )

                        Spacer(
                            modifier =
                                Modifier.height(20.dp)
                        )

                        Button(
                            onClick = {

                                val inputStream =
                                    context.contentResolver
                                        .openInputStream(uri)

                                val tempFile =
                                    File.createTempFile(
                                        "upload",
                                        ".pdf",
                                        context.cacheDir
                                    )

                                inputStream?.use { input ->
                                    tempFile.outputStream()
                                        .use { output ->
                                            input.copyTo(output)
                                        }
                                }

                                val requestFile =
                                    tempFile.asRequestBody(
                                        "application/pdf"
                                            .toMediaTypeOrNull()
                                    )

                                val body =
                                    MultipartBody.Part.createFormData(
                                        "file",
                                        tempFile.name,
                                        requestFile
                                    )

                                Log.d(
                                    "UPLOAD_DEBUG",
                                    "Upload clicked"
                                )

                                viewModel.uploadPdf(body)
                            },

                            modifier =
                                Modifier.fillMaxWidth(),

                            shape =
                                InputRadius,

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        PrimaryPurple
                                )
                        ) {

                            Text(
                                text =
                                    "Upload & Summarize"
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            if (isLoading) {

                Box(
                    modifier =
                        Modifier.fillMaxWidth(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = White
                    )
                }
            }

            if (summary.isNotBlank()) {

                SummaryCard(
                    fileName = fileName,
                    summary = summary,
                    clipboardManager =
                        clipboardManager
                )
            }
        }
    }
}

fun getFileName(
    context: Context,
    uri: Uri
): String {

    var result = "Selected PDF"

    context.contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )?.use { cursor ->

        if (cursor.moveToFirst()) {

            val index =
                cursor.getColumnIndex(
                    OpenableColumns.DISPLAY_NAME
                )

            if (index >= 0) {
                result =
                    cursor.getString(index)
            }
        }
    }

    return result
}