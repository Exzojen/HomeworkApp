package com.example.hwapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hwapp.theme.AppTheme


@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val currentUsername = remember { mutableStateOf("") }

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
                    navController = navController,
                    onLoginSuccess = { username ->
                        currentUsername.value = username
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("main") {
                MainScreen(
                    username = currentUsername.value.ifEmpty { "Гость" },
                    onExitApp = {
                        exitApplication()
                    }
                )
            }
        }
    }
}