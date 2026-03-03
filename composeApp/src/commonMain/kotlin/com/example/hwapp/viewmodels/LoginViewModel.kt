package com.example.hwapp.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.hwapp.states.loginUiState
import com.example.hwapp.labels.loginUiLabel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
class LoginViewModel : BaseViewModel<loginUiLabel.NavigateToMainScreen, loginUiState>(
    initialState = loginUiState.Initial,
) {

    fun onLoginChange(value: String) {
        updateState { copy(login = value) }
    }

    fun onPasswordChange(value: String) {
        updateState { copy(password = value) }
    }

    fun onLoginClick() {
        if (state.value.isLoginButtonActive) return

        updateState {
            copy(
                error = false,
                isLoginButtonActive = true,
            )
        }

        viewModelScope.launch {
            delay(2000)

            val isSuccess = Random.nextBoolean()

            if (isSuccess) {
                acceptLabel(loginUiLabel.NavigateToMainScreen)
            } else {
                updateState {
                    copy(
                        isLoginButtonActive = false,
                        error = true,
                    )
                }
            }
        }
    }
}
