package com.example.hwapp.feature.main_feature.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.hwapp.events.MainUiEvent
import com.example.hwapp.feature.App_main.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainUiEvent, MainUiState>(
    initialState = MainUiState.Initial,
) {

    private val _events = MutableSharedFlow<MainUiEvent>()
    override val events: SharedFlow<MainUiEvent> = _events.asSharedFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        updateState {
            copy(isLoading = true)
        }

        viewModelScope.launch {
            delay(500) // Имитация загрузки сети

            val items = listOf(
                GithubRepoItem(
                    id = 1,
                    owner = "JetBrains",
                    name = "kotlin",
                    description = "The Kotlin Programming Language. A modern, cross-platform, statically typed programming language.",
                    language = "Kotlin",
                    stars = 46500,
                    forks = 5300,
                    avatarUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png"
                ),
                GithubRepoItem(
                    id = 2,
                    owner = "android",
                    name = "architecture-samples",
                    description = "A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.",
                    language = "Kotlin",
                    stars = 43200,
                    forks = 11500,
                    avatarUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png"
                ),
                GithubRepoItem(
                    id = 3,
                    owner = "torvalds",
                    name = "linux",
                    description = "Linux kernel source tree",
                    language = "C",
                    stars = 158000,
                    forks = 52000,
                    avatarUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png"
                ),
                GithubRepoItem(
                    id = 4,
                    owner = "facebook",
                    name = "react",
                    description = "The library for web and native user interfaces.",
                    language = "JavaScript",
                    stars = 210000,
                    forks = 43000,
                    avatarUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png"
                )
            )

            updateState {
                copy(
                    items = items,
                    isLoading = false
                )
            }
        }
    }

    fun onTabSelected(tab: GithubTab) {
        updateState {
            copy(currentTab = tab)
        }
    }

    fun onBackPressed() {
        viewModelScope.launch {
            _events.emit(MainUiEvent.ExitApp)
        }
    }
}

@Immutable
data class GithubRepoItem(
    val id: Int,
    val owner: String,
    val name: String,
    val description: String,
    val language: String,
    val stars: Int,
    val forks: Int,
    val avatarUrl: String
)