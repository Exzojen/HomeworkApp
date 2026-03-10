package composeResources

/**
 * Файл с константами приложения
 * Содержит все строковые значения, используемые в интерфейсе
 */
object StringConstants {
    // ==================== ТЕКСТЫ ЭКРАНА ПРИВЕТСТВИЯ (GREETING SCREEN TEXTS) ====================
    object GreetingScreen{
        // Заголовки
        const val GREETING_MESSAGE = "Добро пожаловать! Ня :3"
        const val CONTINUE_LABEL = "Продолжить"
    }
    // ==================== ТЕКСТЫ ЭКРАНА ВХОДА (LOGIN SCREEN TEXTS) ====================
    object LoginScreen {
        // Заголовки
        const val TITLE = "Авторизация"
        const val LOGIN_LABEL = "Логин"
        const val PASSWORD_LABEL = "Пароль"
        const val ALLOW_LOGIN_TEXT = "Разрешить вход:"
        const val LOGIN_BUTTON_TEXT = "Войти"

    }

    // ==================== ТЕКСТЫ ГЛАВНОГО ЭКРАНА (MAIN SCREEN TEXTS) ====================
    object MainScreen {
        // Заголовки
        const val TITLE = "Главный экран"
        const val WELCOME_MESSAGE = "Добро пожаловать, %s!"
        const val GREETING = "Приветствуем вас в приложении"

        // Тексты кнопок
        const val PROFILE_BUTTON = "Профиль"
        const val SETTINGS_BUTTON = "Настройки"
        const val LOGOUT_BUTTON = "Выйти"

        // Статусы
        const val LOADING = "Загрузка..."
        const val REFRESH = "Обновить"
    }
}