package com.example.hwapp.states

data class loginUiState(
    val username: String ="",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
    val error: String? = null
)
