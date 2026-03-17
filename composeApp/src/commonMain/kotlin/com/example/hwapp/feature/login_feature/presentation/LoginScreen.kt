package com.example.hwapp.feature.login_feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hwapp.events.LoginUiEvent
import composeResources.StringConstants
import composeResources.AppTheme
import composeResources.fontSizeMainCompose
import composeResources.paddingMainCompose
import composeResources.paddingSmallCompose
import composeResources.smallLoadingCircle
import hwapp.composeapp.generated.resources.Res
import hwapp.composeapp.generated.resources.ic_google
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navController: NavController,
    onLoginSuccess: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var allowLogin by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is LoginUiEvent.LoginSuccess -> {
                    onLoginSuccess()
                }

                is LoginUiEvent.LoginError -> {
                    errorMessage = event.message
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
            text = StringConstants.LoginScreen.TITLE,
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
            value = state.username,
            onValueChange = { viewModel.onUsernameChanged(it) },
            label = { Text(StringConstants.LoginScreen.LOGIN_LABEL) },
            isError = errorMessage != null,
            enabled = !state.isLoginButtonActive,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingSmallCompose)
        )

        TextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text(StringConstants.LoginScreen.PASSWORD_LABEL) },
            visualTransformation = PasswordVisualTransformation(),
            isError = errorMessage != null,
            enabled = !state.isLoginButtonActive,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingMainCompose)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingMainCompose),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = StringConstants.LoginScreen.ALLOW_LOGIN_TEXT,
                color = MaterialTheme.colorScheme.onBackground
            )
            Switch(
                checked = allowLogin,
                onCheckedChange = { allowLogin = it },
                enabled = !state.isLoginButtonActive
            )
        }

        Button(
            onClick = {
                if (allowLogin) {
                    viewModel.onLoginClick(state.username)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoginButtonActive
        ) {
            if (state.isLoginButtonActive) {
                CircularProgressIndicator(
                    modifier = Modifier.size(smallLoadingCircle),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(StringConstants.LoginScreen.LOGIN_BUTTON_TEXT)
            }
        }
        GoogleSignInStubButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
            }
        )
    }
}

@Composable
fun GoogleSignInStubButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_google),
            contentDescription = "Google Logo",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = StringConstants.LoginScreen.GOOGLEAUTH_BUTTON_TEXT,
            style = MaterialTheme.typography.labelLarge
        )
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