package com.example.brainbrewai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brainbrewai.presentation.auth.LoginScreen
import com.example.brainbrewai.presentation.auth.forgotpassword.ForgotPasswordScreen
import com.example.brainbrewai.presentation.auth.register.RegisterScreen
import com.example.brainbrewai.presentation.auth.welcome.WelcomeScreen
import com.example.brainbrewai.presentation.chat.ChatScreen
import com.example.brainbrewai.presentation.dashboard.DashboardScreen
import com.example.brainbrewai.presentation.profile.ProfileScreen
import com.example.brainbrewai.presentation.quiz.GenerateQuizScreen
import com.example.brainbrewai.presentation.splash.SplashScreen
import com.example.brainbrewai.presentation.study.StudyScreen

import com.example.brainbrewai.presentation.studyplanner.StudyPlannerScreen

import com.example.brainbrewai.presentation.uploadnotes.UploadNotesScreen
import com.example.brainbrewai.presentation.voicenotes.VoiceNotesScreen

@Composable
fun AppNavGraph() {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,

        startDestination =
            Screen.Splash.route
    ) {

        composable(
            Screen.Splash.route
        ) {

            SplashScreen(navController)
        }

        composable(
            Screen.Welcome.route
        ) {

            WelcomeScreen(navController)
        }

        composable(
            Screen.Login.route
        ) {

            LoginScreen(navController)
        }

        composable(
            Screen.Register.route
        ) {

            RegisterScreen(navController)
        }
        composable(
            Screen.ForgotPassword.route
        ) {
            ForgotPasswordScreen(navController)
        }

        composable(
            Screen.Dashboard.route
        ) {

            DashboardScreen(navController)
        }
        composable(
            Screen.Chat.route
        ) {

            ChatScreen()
        }
        composable(
            Screen.Study.route
        ) {
            StudyScreen(navController)
        }
        composable(
            Screen.Profile.route
        ) {
            ProfileScreen(navController)
        }
        composable(
            Screen.UploadNotes.route
        ) {
            UploadNotesScreen(navController)
        }
        composable(
            Screen.GenerateQuiz.route
        ) {
            GenerateQuizScreen(navController)
        }
        composable(
            Screen.StudyPlanner.route
        ) {
            StudyPlannerScreen(navController)
        }
        composable(
            Screen.VoiceNotes.route
        ) {
            VoiceNotesScreen(navController)
        }
    }
}