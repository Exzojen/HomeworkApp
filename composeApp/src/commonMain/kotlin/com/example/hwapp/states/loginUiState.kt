package com.example.hwapp.states

import androidx.compose.runtime.Immutable

@Immutable
data class loginUiState(
    val login: String,
    val password: String,
    val error: Boolean,
    val isLoginButtonActive: Boolean,
) {
    companion object {
        val Initial = loginUiState(
            login = "",
            password = "",
            error = false,
            isLoginButtonActive = false,
        )
    }
}
