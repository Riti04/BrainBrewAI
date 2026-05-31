package com.example.brainbrewai.navigation

sealed class Screen(
    val route: String
) {

    data object Splash : Screen("splash")

    data object Welcome : Screen("welcome")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Dashboard : Screen("dashboard")

    data object Chat : Screen("chat")

    data object ForgotPassword : Screen("forgot_password")
    data object Study : Screen("study")
    data object Profile : Screen("profile")
    data object UploadNotes: Screen("upload_notes")
    data object GenerateQuiz: Screen("generate_quiz")
    data object StudyPlanner: Screen("study_planner")
    data object VoiceNotes: Screen("voice_notes")

}

