package com.example.brainbrewai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.brainbrewai.navigation.AppNavGraph
import com.example.brainbrewai.ui.theme.BrainBrewAITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            BrainBrewAITheme {

                AppNavGraph()
            }
        }
    }
}