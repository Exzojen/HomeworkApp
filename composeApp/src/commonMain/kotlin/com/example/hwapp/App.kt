package com.example.hwapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
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
                )
            }
        }
    }
}