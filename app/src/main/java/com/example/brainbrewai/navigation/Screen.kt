package com.example.brainbrewai.navigation

sealed class Screen(
    val route: String
) {

    data object Splash : Screen("splash")

    data object Welcome : Screen("welcome")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Dashboard : Screen("dashboard")

    object Chat : Screen("chat")

}