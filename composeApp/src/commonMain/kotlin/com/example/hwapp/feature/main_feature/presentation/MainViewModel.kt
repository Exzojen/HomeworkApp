package com.example.hwapp.feature.main_feature.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.hwapp.events.MainUiEvent
import com.example.hwapp.feature.App_main.BaseViewModel
import com.example.hwapp.feature.App_main.UserRepository
import com.example.hwapp.feature.main_feature.data.AppDependencies
import com.example.hwapp.feature.main_feature.data.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainUiEvent, MainUiState>(
    initialState = MainUiState.Initial,
) {
    private val _events = MutableSharedFlow<MainUiEvent>()
    override val events: SharedFlow<MainUiEvent> = _events.asSharedFlow()

    private val repository = GithubRepository(AppDependencies.dao)
    private val searchQuery = MutableStateFlow("")


    init {
        val savedName = UserRepository.currentUsername.value
        updateState { copy(username = savedName) }

        observeSearch()
    }
    fun onQueryChanged(newQuery: String) {
        updateState { copy(query = newQuery) }
        searchQuery.value = newQuery
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(600L)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        if (query.isBlank()) {
                            emit(Result.success(emptyList<GithubRepoItem>()))
                        } else {
                            updateState { copy(isLoading = true, errorMessage = null, items = emptyList(), page = 1, isEndOfList = false, isListEmpty = false) }
                            emit(repository.searchRepositories(query, page = 1))
                        }
                    }
                }
                .collectLatest { result ->
                    if (searchQuery.value.isBlank()) {
                        updateState { copy(items = emptyList(), isListEmpty = false, isLoading = false) }
                    } else {
                        result.onSuccess { newItems ->
                            updateState {
                                copy(
                                    isLoading = false,
                                    items = newItems,
                                    isListEmpty = newItems.isEmpty(),
                                    isEndOfList = newItems.isEmpty()
                                )
                            }
                        }.onFailure { error ->
                            updateState { copy(isLoading = false, errorMessage = error.message ?: "Ошибка сети") }
                        }
                    }
                }
        }
    }

    fun loadNextPage() {
        val currentState = state.value
        if (currentState.isPaging || currentState.isLoading || currentState.isEndOfList || currentState.query.isBlank()) return

        updateState { copy(isPaging = true, errorMessage = null) }

        viewModelScope.launch {
            val nextPage = currentState.page + 1
            val result = repository.searchRepositories(currentState.query, nextPage)

            result.onSuccess { newItems ->
                updateState {
                    copy(
                        isPaging = false,
                        items = items + newItems,
                        page = nextPage,
                        isEndOfList = newItems.isEmpty()
                    )
                }
            }.onFailure { error ->
                updateState { copy(isPaging = false, errorMessage = error.message ?: "Ошибка загрузки страницы") }
            }
        }
    }
    fun refresh() {
        val currentQuery = searchQuery.value
        if (currentQuery.isBlank()) return

        updateState { copy(isRefreshing = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.searchRepositories(currentQuery, page = 1)

            result.onSuccess { newItems ->
                updateState {
                    copy(
                        isRefreshing = false,
                        items = newItems,
                        page = 1,
                        isListEmpty = newItems.isEmpty(),
                        isEndOfList = newItems.isEmpty()
                    )
                }
            }.onFailure { error ->
                updateState { copy(isRefreshing = false, errorMessage = error.message ?: "Ошибка обновления") }
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            UserRepository.saveUsername("")
            _events.emit(MainUiEvent.Logout)
        }
    }
    fun retry() {
        val currentQuery = searchQuery.value
        searchQuery.value = ""
        searchQuery.value = currentQuery
    }

    fun onTabSelected(tab: GithubTab) { updateState { copy(currentTab = tab) } }
    fun onBackPressed() { viewModelScope.launch { _events.emit(MainUiEvent.ExitApp) } }
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