package com.example.hwapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hwapp.states.loginUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(loginUiState())
    val uiState: StateFlow<loginUiState> = _uiState

    init {
        viewModelScope.launch {}
        fun onUsernameChanged(newUsername: String) {
            _uiState.update { currentState ->
                currentState.copy(
                    username = newUsername,
                    isLoginButtonActive = newUsername.isNotBlank() &&
                            currentState.password.isNotBlank(),
                    error = null
                )
            }
        }
    }
}


//class LoginViewModel : BaseViewModel<UiLoginLabel.NavigateToMainScreen, UiLoginState>(
//    initialState = UiLoginState.Initial,
//) {
//
//    fun onLoginChange(value: String) {
//        updateState { copy(login = value) }
//    }
//
//    fun onPasswordChange(value: String) {
//        updateState { copy(password = value) }
//    }
//
//    fun onLoginClick() {
//        if (state.value.loading) return
//
//        updateState {
//            copy(
//                error = false,
//                loading = true,
//            )
//        }
//
//        viewModelScope.launch {
//            delay(2000)
//
//            val isSuccess = Random.nextBoolean()
//
//            if (isSuccess) {
//                acceptLabel(UiLoginLabel.NavigateToMainScreen)
//            } else {
//                updateState {
//                    copy(
//                        loading = false,
//                        error = true,
//                    )
//                }
//            }
//        }
//    }
