package com.example.hwapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hwapp.theme.fontSizeMainCompose
import com.example.hwapp.theme.paddingMainCompose
import com.example.hwapp.theme.paddingSmallCompose
import com.example.hwapp.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingMainCompose),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Авторизация",
            fontSize = fontSizeMainCompose,
            modifier = Modifier.padding(bottom = paddingMainCompose),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (state.error) {
            Text(
                text = "Ошибка входа. Попробуйте снова.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = paddingSmallCompose)
            )
        }

        TextField(
            value = state.login,
            onValueChange = viewModel::onLoginChange,
            label = { Text("Email") },
            isError = state.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingSmallCompose)
        )

        TextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            isError = state.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingMainCompose)
        )

        Button(
            onClick = viewModel::onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isLoginButtonActive.not()
        ) {
            if (state.isLoginButtonActive) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Войти")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}