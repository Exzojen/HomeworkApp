package com.example.hwapp.feature.main_feature.presentation

import androidx.compose.runtime.Immutable

enum class GithubTab {
    HOME, INBOX, EXPLORE, PROFILE
}

@Immutable
data class MainUiState(
    val items: List<GithubRepoItem>,
    val isLoading: Boolean,
    val currentTab: GithubTab
) {
    companion object {
        val Initial = MainUiState(
            items = emptyList(),
            isLoading = false,
            currentTab = GithubTab.HOME
        )
    }
}