package com.muahmmadfadhillaharrobbi0021.studytimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.AboutAppScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.HomeScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.SettingsScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.TimerScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartClick = {
                    navController.navigate("timer")
                },
                onHistoryClick = {
                    navController.navigate("history")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onAboutClick = {
                    navController.navigate("about")
                }
            )
        }

        composable("timer") {
            TimerScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("history") {
        }

        composable("settings") {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                // TAMBAHKAN INI: Agar tombol 'Tentang Aplikasi' di Settings bisa diklik
                onAboutClick = {
                    navController.navigate("about")
                }
            )
        }

        composable("about") {
            AboutAppScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}