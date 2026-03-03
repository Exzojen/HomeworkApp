package com.example.hwapp.states

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val login: String,
    val password: String,
    val error: Boolean,
    val isLoginButtonActive: Boolean,
) {
    companion object {
        val Initial = LoginUiState(
            login = "",
            password = "",
            error = false,
            isLoginButtonActive = false,
        )
    }
}
