package composeResources

/**
 * Файл с константами приложения
 * Содержит все строковые значения, используемые в интерфейсе
 */
object StringConstants {
    // ==================== ТЕКСТЫ ЭКРАНА ПРИВЕТСТВИЯ (GREETING SCREEN TEXTS) ====================
    object GreetingScreen{
        // Заголовки
        const val GREETING_SCREEN_GREETING_MESSAGE = "Добро пожаловать! Ня :3"
        const val GREETING_SCREEN_CONTINUE_LABEL = "Продолжить"
    }
    // ==================== ТЕКСТЫ ЭКРАНА ВХОДА (LOGIN SCREEN TEXTS) ====================
    object LoginScreen {
        // Заголовки
        const val TITLE = "Авторизация"
        const val LOGIN_LABEL = "Логин"
        const val PASSWORD_LABEL = "Пароль"
        const val ALLOW_LOGIN_TEXT = "Разрешить вход:"
        const val LOGIN_BUTTON_TEXT = "Войти"
        const val GOOGLEAUTH_BUTTON_TEXT = "Sign in with Google"
    }

    // ==================== ТЕКСТЫ ГЛАВНОГО ЭКРАНА (MAIN SCREEN TEXTS) ====================
    object MainScreen {
        // Заголовки
        const val MAIN_SCREEN_USERNAME_PLACEHOLDER = "Разработчик"
        const val MAIN_SCREEN_REPO_SEARCHBAR_TEXT = "Поиск репозиториев..."
        const val MAIN_SCREEN_RETRY_ACTION = "Повторить"
        const val MAIN_SCREEN_SEARCH_ISBLANK = "По вашему запросу ничего не найдено"
        const val MAIN_SCREEN_TAB_HOME = "Главная"
        const val MAIN_SCREEN_TAB_INBOX = "Уведомления"
        const val MAIN_SCREEN_TAB_EXPLORE = "Обзор"
        const val MAIN_SCREEN_TAB_PROFILE = "Профиль"

        fun mainScreenPagingErrorLabel(error: String): String {
            return "Ошибка загрузки: $error"
        }
        fun stubScreenContent(tabName: String): String {
            return "Содержимое экрана: $tabName"
        }
    }
}