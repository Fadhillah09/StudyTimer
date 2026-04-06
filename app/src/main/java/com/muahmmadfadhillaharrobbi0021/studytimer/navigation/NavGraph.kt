    package com.muahmmadfadhillaharrobbi0021.studytimer.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import com.muahmmadfadhillaharrobbi0021.studytimer.screen.AboutAppScreen
    import com.muahmmadfadhillaharrobbi0021.studytimer.screen.HistoryScreen
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
                    onStartClick = { name, duration, cat, conc, mode ->
                        navController.navigate("timer/$name/$duration/$cat/$conc/$mode")
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
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable("settings") {
                SettingsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
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