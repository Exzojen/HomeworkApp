package com.example.hwapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.hwapp.theme.fontSizeMainCompose
import com.example.hwapp.theme.paddingMainCompose

@Composable
fun MainScreen(
    username: String = "Гость"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingMainCompose),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Добро пожаловать, $username!",
            fontSize = fontSizeMainCompose,
            fontWeight = FontWeight.Bold
        )
    }
}