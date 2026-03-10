package com.example.hwapp.feature.App_main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hwapp.feature.greeting_feature.GreetingScreen
import com.example.hwapp.exitApplication
import com.example.hwapp.feature.App_main.UserRepository.currentUsername
import com.example.hwapp.feature.login_feature.presentation.LoginScreen
import com.example.hwapp.feature.main_feature.presentation.MainScreen
import composeResources.AppTheme


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
                    navController = navController,
                    onLoginSuccess = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("main") {
                MainScreen(
                    onExitApp = {
                        exitApplication()
                    }
                )
            }
        }
    }
}