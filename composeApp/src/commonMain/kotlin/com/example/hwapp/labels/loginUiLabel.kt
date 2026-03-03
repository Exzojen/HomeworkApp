package com.example.hwapp.labels

sealed interface loginUiLabel {
    data object NavigateToMainScreen : loginUiLabel
}