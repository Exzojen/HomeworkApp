package com.example.hwapp.states

import androidx.compose.runtime.Immutable
import com.example.hwapp.viewmodels.VKPostItem

@Immutable
data class MainUiState(
    val items: List<VKPostItem>,
    val isLoading: Boolean,
) {
    companion object {
        val Initial = MainUiState(
            items = emptyList(),
            isLoading = false,
        )
    }
}