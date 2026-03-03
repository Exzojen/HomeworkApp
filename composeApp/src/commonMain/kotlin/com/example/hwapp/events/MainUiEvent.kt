package com.example.hwapp.events

sealed class MainUiEvent {
    data object ExitApp : MainUiEvent()
}