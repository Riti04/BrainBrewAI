package com.example.brainbrewai.presentation.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.brainbrewai.navigation.Screen
import com.example.brainbrewai.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun ProfileScreen(
    navController: NavController
) {

    val user = FirebaseAuth.getInstance().currentUser

    var name by remember {
        mutableStateOf(
            user?.displayName ?: ""
        )
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var isSaving by remember {
        mutableStateOf(false)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            imageUri = uri
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGradient)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "My Profile",
                style = MaterialTheme.typography.headlineMedium,
                color = White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                contentAlignment = Alignment.BottomEnd
            ) {

                if (imageUri != null) {

                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )

                } else {

                    Surface(
                        modifier = Modifier.size(110.dp),
                        shape = CircleShape,
                        color = White
                    ) {}
                }

                FloatingActionButton(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    containerColor = PrimaryPurple
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = CardRadius
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text("Full Name")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = InputRadius
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = user?.email ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Email")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = InputRadius
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {

                            isSaving = true

                            val updates =
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build()

                            user?.updateProfile(updates)
                                ?.addOnCompleteListener {
                                    isSaving = false
                                }
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = InputRadius,

                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryPurple
                        )
                    ) {

                        if (isSaving) {
                            CircularProgressIndicator(
                                color = White
                            )
                        } else {
                            Text("Save Changes")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    FirebaseAuth.getInstance().signOut()

                    navController.navigate(
                        Screen.Login.route
                    ) {
                        popUpTo(0)
                    }
                },

                modifier = Modifier.fillMaxWidth(),

                shape = InputRadius,

                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPurple
                )
            ) {

                Icon(
                    Icons.Default.Logout,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Logout")
            }
        }
    }
}