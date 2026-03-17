package composeResources

import androidx.compose.ui.graphics.Color

// Теплая светлая палитра
val WarmLightPrimary = Color(0xFFB45F3A) // Терракотовый
val WarmLightSecondary = Color(0xFFD9845B) // Персиковый
val WarmLightTertiary = Color(0xFFE6B89C) // Светло-персиковый

val WarmLightBackground = Color(0xFFFCF5ED) // Теплый кремовый
val WarmLightSurface = Color(0xFFFFFAF2) // Светло-кремовый
val WarmLightSurfaceVariant = Color(0xFFF5E8DA) // Теплый бежевый

val WarmLightError = Color(0xFFBA4A4A) // Мягкий красный
val WarmLightSuccess = Color(0xFF5D8C6B) // Мятно-зеленый
val WarmLightWarning = Color(0xFFE5A43B) // Янтарный
val WarmLightInfo = Color(0xFF4A7A9C) // Приглушенный синий

val WarmLightOnPrimary = Color(0xFFFFFFFF) // Белый
val WarmLightOnSecondary = Color(0xFF2D2D2D) // Темно-серый
val WarmLightOnTertiary = Color(0xFF2D2D2D)
val WarmLightOnBackground = Color(0xFF3A2E28) // Темно-коричневый
val WarmLightOnSurface = Color(0xFF3A2E28)
val WarmLightOnSurfaceVariant = Color(0xFF5C4E45) // Коричнево-серый
val WarmLightOnError = Color(0xFFFFFFFF)

// Сумрачная темная палитра
val DarkTwilightPrimary = Color(0xFFB7A093) // Пыльная роза
val DarkTwilightSecondary = Color(0xFF7C6A5F) // Гречишный
val DarkTwilightTertiary = Color(0xFF5D4E46) // Темный гречишный

val DarkTwilightBackground = Color(0xFF1E1A18) // Антрацит с теплым подтоном
val DarkTwilightSurface = Color(0xFF2A2421) // Темный гречишный
val DarkTwilightSurfaceVariant = Color(0xFF3A322E) // Мокрый асфальт

val DarkTwilightError = Color(0xFFCF6679) // Мягкий розовый
val DarkTwilightSuccess = Color(0xFF6A8E7A) // Приглушенный зеленый
val DarkTwilightWarning = Color(0xFFE5A43B) // Янтарный
val DarkTwilightInfo = Color(0xFF6B8FA0) // Приглушенный синий

val DarkTwilightOnPrimary = Color(0xFF1E1A18) // Темный фон
val DarkTwilightOnSecondary = Color(0xFFEDE0D7) // Светлый бежевый
val DarkTwilightOnTertiary = Color(0xFFEDE0D7)
val DarkTwilightOnBackground = Color(0xFFE8DCD3) // Светлый бежевый
val DarkTwilightOnSurface = Color(0xFFE8DCD3)
val DarkTwilightOnSurfaceVariant = Color(0xFFC9B9AF) // Пыльный розовый
val DarkTwilightOnError = Color(0xFF1E1A18)

// Семантические цвета для палитры (можно использовать для удобства)
val ExtendedLightColors = mapOf(
    "primary" to WarmLightPrimary,
    "secondary" to WarmLightSecondary,
    "tertiary" to WarmLightTertiary,
    "background" to WarmLightBackground,
    "surface" to WarmLightSurface,
    "surfaceVariant" to WarmLightSurfaceVariant,
    "error" to WarmLightError,
    "success" to WarmLightSuccess,
    "warning" to WarmLightWarning,
    "info" to WarmLightInfo
)

val ExtendedDarkColors = mapOf(
    "primary" to DarkTwilightPrimary,
    "secondary" to DarkTwilightSecondary,
    "tertiary" to DarkTwilightTertiary,
    "background" to DarkTwilightBackground,
    "surface" to DarkTwilightSurface,
    "surfaceVariant" to DarkTwilightSurfaceVariant,
    "error" to DarkTwilightError,
    "success" to DarkTwilightSuccess,
    "warning" to DarkTwilightWarning,
    "info" to DarkTwilightInfo
)
