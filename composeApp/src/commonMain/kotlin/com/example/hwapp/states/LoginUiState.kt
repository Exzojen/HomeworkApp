package com.example.hwapp.states

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val username: String,
    val password: String,
    val error: Boolean,
    val isLoginButtonActive: Boolean,
) {
    companion object {
        val Initial = LoginUiState(
            username = "",
            password = "",
            error = false,
            isLoginButtonActive = false,
        )
    }
}
