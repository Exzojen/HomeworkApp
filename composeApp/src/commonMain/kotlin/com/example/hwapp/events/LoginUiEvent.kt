package com.example.hwapp.events

sealed class LoginUiEvent {
    data class LoginSuccess(
        val user: UserData
    ) : LoginUiEvent()

    data class LoginError(
        val message: String
    ) : LoginUiEvent()

//    data object NavigateBack : LoginUiEvent()
}

data class UserData(
    val username: String,
    val userId: String,
    val token: String,
    val email: String? = null
)