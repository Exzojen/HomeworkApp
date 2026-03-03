package com.example.hwapp.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.hwapp.states.LoginUiState
import com.example.hwapp.events.LoginUiEvent
import com.example.hwapp.events.UserData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
class LoginViewModel : BaseViewModel<LoginUiEvent, LoginUiState>(
    initialState = LoginUiState.Initial,
) {

    private val _events = MutableSharedFlow<LoginUiEvent>()
    override val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

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
                val userData = UserData(
                    username = state.value.login,
                    userId = Random.nextInt(1000, 9999).toString(),
                    token = "token_${Random.nextInt(100000, 999999)}",
                    email = "${state.value.login}@example.com"
                )
                _events.emit(LoginUiEvent.LoginSuccess(user = userData))
            } else {
                _events.emit(LoginUiEvent.LoginError(message = "Неверный логин или пароль"))
                updateState {
                    copy(
                        isLoginButtonActive = false,
                        error = true,
                    )
                }
            }
        }
    }

    fun onNavigateBack() {
        viewModelScope.launch {
            _events.emit(LoginUiEvent.NavigateBack)
        }
    }
}
