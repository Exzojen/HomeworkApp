package com.example.hwapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.hwapp.theme.AppTheme


@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "greeting"
        ) {
            composable("greeting") {
                GreetingScreen(
                    onContinueClicked = {
                        navController.navigate("login") {
                            popUpTo("greeting") { inclusive = true }
                        }
                    }
                )
            }
            composable("login") {
                LoginScreen(
                    navController = navController
                )
            }
            composable(
                "main/{username}",
                arguments = listOf(navArgument("username") { type = NavType.StringType })
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username") ?: "Гость"
                MainScreen(username = username)
            }
        }
    }
}