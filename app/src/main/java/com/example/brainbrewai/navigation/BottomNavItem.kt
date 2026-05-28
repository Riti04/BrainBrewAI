package com.example.brainbrewai.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Dashboard : BottomNavItem(
        "dashboard",
        "Dashboard",
        Icons.Default.Home
    )

    object Study : BottomNavItem(
        "study",
        "Study",
        Icons.Default.MenuBook
    )

    object Profile : BottomNavItem(
        "profile",
        "Profile",
        Icons.Default.AccountCircle
    )
}