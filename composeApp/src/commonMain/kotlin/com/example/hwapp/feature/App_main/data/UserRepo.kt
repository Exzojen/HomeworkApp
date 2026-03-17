package com.example.hwapp.feature.App_main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserRepository {
    private val _currentUsername = MutableStateFlow("Developer")
    val currentUsername: StateFlow<String> = _currentUsername.asStateFlow()

    fun saveUsername(name: String) {
        _currentUsername.value = name
    }
}