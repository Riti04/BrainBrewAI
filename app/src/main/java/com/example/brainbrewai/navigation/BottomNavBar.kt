package com.example.brainbrewai.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.brainbrewai.ui.theme.PrimaryPurple
import com.example.brainbrewai.ui.theme.White

@Composable
fun BottomNavBar(
    navController: NavController
) {

    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Study,
        BottomNavItem.Profile
    )

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value
            ?.destination
            ?.route

    NavigationBar(

        containerColor = White,
        tonalElevation = 10.dp

    ) {

        items.forEach { item ->

            NavigationBarItem(

                selected =
                    currentRoute == item.route,

                onClick = {

                    navController.navigate(item.route) {

                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = {

                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                label = {
                    Text(item.title)
                },

                colors =
                    NavigationBarItemDefaults.colors(

                        selectedIconColor =
                            White,

                        selectedTextColor =
                            PrimaryPurple,

                        indicatorColor =
                            PrimaryPurple,

                        unselectedIconColor =
                            MaterialTheme.colorScheme.onSurfaceVariant,

                        unselectedTextColor =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
        }
    }
}