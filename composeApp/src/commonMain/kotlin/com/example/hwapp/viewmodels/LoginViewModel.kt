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

    fun onUsernameChanged(value: String) {
        updateState { copy(username = value) }
    }

    fun onPasswordChanged(value: String) {
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

            val userData = UserData(
                username = state.value.username,
                userId = Random.nextInt(1000, 9999).toString(),
                token = "token_${Random.nextInt(100000, 999999)}",
                email = "${state.value.username}@example.com"
            )
            _events.emit(LoginUiEvent.LoginSuccess(user = userData))
        }
    }

//    fun onNavigateBack() {
//        viewModelScope.launch {
//            _events.emit(LoginUiEvent.NavigateBack)
//        }
//    }
}