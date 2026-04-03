package com.muahmmadfadhillaharrobbi0021.studytimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.muahmmadfadhillaharrobbi0021.studytimer.navigation.NavGraph
import com.muahmmadfadhillaharrobbi0021.studytimer.ui.theme.StudyTimerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            StudyTimerTheme {
                StudyTimerApp()
            }
        }
    }
}

@Composable
fun StudyTimerApp() {

    val navController = rememberNavController()

    Surface {
        NavGraph(navController)
    }
}