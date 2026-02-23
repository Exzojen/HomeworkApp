package com.example.hwapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
@Composable
fun App() {
    MaterialTheme {
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
//fun App() {
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    AsyncImage(
//                        model = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png",
//                        contentDescription = null,
//                        // Показываем заглушку во время загрузки и при ошибке
//                        placeholder = painterResource(Res.drawable.compose_multiplatform),
//                        error = painterResource(Res.drawable.compose_multiplatform),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    Text("Compose: $greeting")
//                }
//            }
//        }
//    }
//}