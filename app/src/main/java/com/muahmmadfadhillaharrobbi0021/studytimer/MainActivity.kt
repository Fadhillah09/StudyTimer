package com.muahmmadfadhillaharrobbi0021.studytimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.muahmmadfadhillaharrobbi0021.studytimer.navigation.NavGraph
import com.muahmmadfadhillaharrobbi0021.studytimer.ui.theme.StudyTimerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StudyTimerTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0A0A0A)
                ) {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}