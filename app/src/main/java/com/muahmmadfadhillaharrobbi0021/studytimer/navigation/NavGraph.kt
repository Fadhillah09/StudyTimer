package com.muahmmadfadhillaharrobbi0021.studytimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.HomeScreen

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
                }
            )

        }

        composable("timer") {
        }

        composable("history") {
        }

        composable("settings") {
        }

    }

}