package com.example.hwapp.feature.App_main

import  androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<Event, State>(initialState: State) : ViewModel() {


    private val mutableState = MutableStateFlow(initialState)
    val state: StateFlow<State> = mutableState.asStateFlow()

    fun updateState(block: State.() -> State) {
        mutableState.update { it.block() }
    }

    abstract val events: SharedFlow<Event>
}

