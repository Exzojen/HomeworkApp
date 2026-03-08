package com.example.hwapp.theme

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

    // ==================== ТЕКСТЫ ЭКРАНА ПРОФИЛЯ (PROFILE SCREEN TEXTS) ====================
    object ProfileScreen {
        // Заголовки
        const val TITLE = "Профиль пользователя"
        const val PERSONAL_INFO = "Личная информация"

        // Поля
        const val USERNAME = "Имя пользователя"
        const val EMAIL = "Email"
        const val PHONE = "Телефон"
        const val REGISTRATION_DATE = "Дата регистрации"

        // Кнопки
        const val EDIT_BUTTON = "Редактировать"
        const val SAVE_BUTTON = "Сохранить"
        const val CANCEL_BUTTON = "Отмена"
    }

    // ==================== ТЕКСТЫ ЭКРАНА НАСТРОЕК (SETTINGS SCREEN TEXTS) ====================
    object SettingsScreen {
        // Заголовки
        const val TITLE = "Настройки"

        // Разделы настроек
        const val APPEARANCE = "Внешний вид"
        const val NOTIFICATIONS = "Уведомления"
        const val LANGUAGE = "Язык"
        const val SECURITY = "Безопасность"
        const val ABOUT = "О приложении"

        // Опции
        const val DARK_MODE = "Темная тема"
        const val PUSH_NOTIFICATIONS = "Push-уведомления"
        const val EMAIL_NOTIFICATIONS = "Email-уведомления"
        const val CHANGE_PASSWORD = "Сменить пароль"
        const val APP_VERSION = "Версия приложения"

        // Кнопки
        const val SAVE_SETTINGS = "Сохранить настройки"
        const val RESET_DEFAULTS = "Сбросить настройки"
    }

    // ==================== ОБЩИЕ ТЕКСТЫ (COMMON TEXTS) ====================
    object Common {
        // Кнопки действий
        const val OK = "OK"
        const val CANCEL = "Отмена"
        const val YES = "Да"
        const val NO = "Нет"
        const val CLOSE = "Закрыть"
        const val BACK = "Назад"
        const val CONTINUE = "Продолжить"
        const val SKIP = "Пропустить"
        const val DONE = "Готово"

        // Статусы
        const val SUCCESS = "Успешно"
        const val ERROR = "Ошибка"
        const val WARNING = "Предупреждение"
        const val INFO = "Информация"
        const val LOADING = "Загрузка..."

        // Сообщения
        const val NETWORK_ERROR = "Ошибка подключения к сети"
        const val SERVER_ERROR = "Ошибка сервера"
        const val DATA_SAVED = "Данные сохранены"
        const val CHANGES_SAVED = "Изменения сохранены"
        const val CONFIRM_EXIT = "Вы действительно хотите выйти?"
    }

    // ==================== НАЗВАНИЯ КЛЮЧЕЙ ДЛЯ ХРАНЕНИЯ ДАННЫХ (DATA STORAGE KEYS) ====================
    object PrefsKeys {
        // Ключи для SharedPreferences
        const val IS_LOGGED_IN = "is_logged_in"
        const val USERNAME = "username"
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
        const val DARK_MODE_ENABLED = "dark_mode_enabled"
        const val NOTIFICATIONS_ENABLED = "notifications_enabled"
        const val FIRST_LAUNCH = "first_launch"
    }

    // ==================== ПАРАМЕТРЫ НАВИГАЦИИ (NAVIGATION PARAMETERS) ====================
    object NavigationArgs {
        const val USER_ID_ARG = "userId"
        const val USERNAME_ARG = "username"
        const val SCREEN_TITLE_ARG = "title"
    }

    // ==================== ТЕСТОВЫЕ ДАННЫЕ (TEST DATA) ====================
    object TestData {
        // Тестовые учетные данные
        const val TEST_USERNAME = "admin"
        const val TEST_PASSWORD = "admin"

        // Тестовые сообщения
        const val PREVIEW_TEXT = "Предварительный просмотр"
        const val PLACEHOLDER_TEXT = "Тестовый текст"
    }
}