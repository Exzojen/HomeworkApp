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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hwapp.events.LoginUiEvent
import com.example.hwapp.theme.AppTheme
import com.example.hwapp.theme.fontSizeMainCompose
import com.example.hwapp.theme.paddingMainCompose
import com.example.hwapp.theme.paddingSmallCompose
import com.example.hwapp.viewmodels.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navController: NavController,
    onLoginSuccess: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is LoginUiEvent.LoginSuccess -> {
                    onLoginSuccess(event.user.username)
                }

                is LoginUiEvent.LoginError -> {
                    errorMessage = event.message
                }

                LoginUiEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
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

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = paddingSmallCompose)
            )
        }

        TextField(
            value = state.login,
            onValueChange = viewModel::onLoginChange,
            label = { Text("Логин") },
            isError = errorMessage != null,
            enabled = !state.isLoginButtonActive,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingSmallCompose)
        )

        TextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            isError = errorMessage != null,
            enabled = !state.isLoginButtonActive,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingMainCompose)
        )

        Button(
            onClick = viewModel::onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoginButtonActive
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

        TextButton(
            onClick = viewModel::onNavigateBack,
            modifier = Modifier.padding(top = paddingSmallCompose)
        ) {
            Text("Назад")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}