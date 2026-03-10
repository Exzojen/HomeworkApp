package com.example.hwapp.feature.main_feature.presentation

import androidx.compose.runtime.Immutable

enum class GithubTab {
    HOME, INBOX, EXPLORE, PROFILE
}
@Immutable
data class MainUiState(
    val username: String = "",
    val query: String = "",
    val items: List<GithubRepoItem> = emptyList(),
    val isLoading: Boolean = false,
    val isPaging: Boolean = false,
    val errorMessage: String? = null,
    val isListEmpty: Boolean = false,
    val currentTab: GithubTab = GithubTab.HOME,
    val page: Int = 1,
    val isEndOfList: Boolean = false
) {
    companion object {
        val Initial = MainUiState()
    }
}