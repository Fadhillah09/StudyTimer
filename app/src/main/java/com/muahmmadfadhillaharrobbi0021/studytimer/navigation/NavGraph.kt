package com.muahmmadfadhillaharrobbi0021.studytimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.AboutScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.CourseScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.EditSessionScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.HistoryScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.HomeScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.RecycleBinScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.SettingsScreen
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.TimerScreen

const val KEY_SESSION_ID = "sessionId"

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartClick = { name, duration, cat, conc, mode ->
                    navController.navigate("timer/$name/$duration/$cat/$conc/$mode")
                },
                onHistoryClick = { navController.navigate("history") },
                onSettingsClick = { navController.navigate("settings") },
                onAboutClick = { navController.navigate("about") }
            )
        }

        composable("timer/{name}/{duration}/{cat}/{conc}/{mode}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val duration = backStackEntry.arguments?.getString("duration")?.toIntOrNull() ?: 25
            val cat = backStackEntry.arguments?.getString("cat") ?: ""
            val conc = backStackEntry.arguments?.getString("conc")?.toIntOrNull() ?: 2
            val mode = backStackEntry.arguments?.getString("mode") ?: "Focus"
            TimerScreen(
                onBackClick = { navController.popBackStack() },
                activityName = name,
                durationMinutes = duration,
                category = cat,
                concentration = conc,
                mode = mode
            )
        }

        composable("history") {
            HistoryScreen(
                onBackClick = { navController.popBackStack() },
                onItemClick = { id -> navController.navigate("edit/$id") },
                onRecycleBinClick = { navController.navigate("recycle_bin") }
            )
        }

        composable(
            route = "edit/{$KEY_SESSION_ID}",
            arguments = listOf(navArgument(KEY_SESSION_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(KEY_SESSION_ID)
            EditSessionScreen(
                sessionId = id,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onAboutClick = { navController.navigate("about") },
                onCoursesClick = { navController.navigate("courses") }
            )
        }

        composable("about") {
            AboutScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("courses") {
            CourseScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("recycle_bin") {
            RecycleBinScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}