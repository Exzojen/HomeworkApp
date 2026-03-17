package com.example.hwapp.events

sealed class MainUiEvent {
    data object ExitApp : MainUiEvent()
    object Logout : MainUiEvent()
}