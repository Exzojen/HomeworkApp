package com.example.hwapp.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.hwapp.states.MainUiState
import com.example.hwapp.events.MainUiEvent
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
            delay(500) // Имитация загрузки

            val items = listOf(
                VKPostItem(
                    id = 1,
                    author = "VK Team",
                    title = "Добро пожаловать в VK!",
                    description = "Это социальная сеть нового поколения",
                    imageUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png",
                    likes = 1250,
                    comments = 340,
                    shares = 125
                ),
                VKPostItem(
                    id = 2,
                    author = "Разработчик",
                    title = "Новое обновление приложения",
                    description = "Мы добавили много новых функций для вашего удобства",
                    imageUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png",
                    likes = 890,
                    comments = 210,
                    shares = 87
                ),
                VKPostItem(
                    id = 3,
                    author = "Сообщество",
                    title = "Интересное событие",
                    description = "Приглашаем вас на онлайн конференцию",
                    imageUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png",
                    likes = 2100,
                    comments = 450,
                    shares = 310
                ),
                VKPostItem(
                    id = 4,
                    author = "Новости",
                    title = "Обновление безопасности",
                    description = "Мы улучшили систему защиты вашей учетной записи",
                    imageUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png",
                    likes = 567,
                    comments = 123,
                    shares = 89
                ),
                VKPostItem(
                    id = 5,
                    author = "Партнер",
                    title = "Специальное предложение",
                    description = "Эксклюзивный контент для наших пользователей",
                    imageUrl = "https://img1.picmix.com/output/stamp/normal/2/6/4/0/2410462_d5309.png",
                    likes = 1890,
                    comments = 410,
                    shares = 245
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

    fun onBackPressed() {
        viewModelScope.launch {
            _events.emit(MainUiEvent.ExitApp)
        }
    }
}

@Immutable
data class VKPostItem(
    val id: Int,
    val author: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val likes: Int,
    val comments: Int,
    val shares: Int
)