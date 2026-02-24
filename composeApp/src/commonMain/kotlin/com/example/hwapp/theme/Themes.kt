package com.example.hwapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val LightColorScheme = lightColorScheme(
    primary = WarmLightPrimary,
    onPrimary = WarmLightOnPrimary,
    primaryContainer = WarmLightTertiary,
    onPrimaryContainer = WarmLightOnBackground,

    secondary = WarmLightSecondary,
    onSecondary = WarmLightOnSecondary,
    secondaryContainer = WarmLightSurfaceVariant,
    onSecondaryContainer = WarmLightOnSurfaceVariant,

    tertiary = WarmLightTertiary,
    onTertiary = WarmLightOnTertiary,
    tertiaryContainer = WarmLightSurface,
    onTertiaryContainer = WarmLightOnSurface,

    background = WarmLightBackground,
    onBackground = WarmLightOnBackground,

    surface = WarmLightSurface,
    onSurface = WarmLightOnSurface,
    surfaceVariant = WarmLightSurfaceVariant,
    onSurfaceVariant = WarmLightOnSurfaceVariant,

    error = WarmLightError,
    onError = WarmLightOnError,
    errorContainer = WarmLightError.copy(alpha = 0.1f).compositeOver(WarmLightSurface),
    onErrorContainer = WarmLightError,

    outline = WarmLightOnSurfaceVariant.copy(alpha = 0.5f),
    outlineVariant = WarmLightSurfaceVariant,

    inverseSurface = DarkTwilightSurface,
    inverseOnSurface = DarkTwilightOnSurface,
    inversePrimary = DarkTwilightPrimary,

    surfaceTint = WarmLightPrimary,
    scrim = Color.Black.copy(alpha = 0.4f)
)

val DarkColorScheme = darkColorScheme(
    primary = DarkTwilightPrimary,
    onPrimary = DarkTwilightOnPrimary,
    primaryContainer = DarkTwilightTertiary,
    onPrimaryContainer = DarkTwilightOnSurface,

    secondary = DarkTwilightSecondary,
    onSecondary = DarkTwilightOnSecondary,
    secondaryContainer = DarkTwilightSurfaceVariant,
    onSecondaryContainer = DarkTwilightOnSurfaceVariant,

    tertiary = DarkTwilightTertiary,
    onTertiary = DarkTwilightOnTertiary,
    tertiaryContainer = DarkTwilightSurface,
    onTertiaryContainer = DarkTwilightOnSurface,

    background = DarkTwilightBackground,
    onBackground = DarkTwilightOnBackground,

    surface = DarkTwilightSurface,
    onSurface = DarkTwilightOnSurface,
    surfaceVariant = DarkTwilightSurfaceVariant,
    onSurfaceVariant = DarkTwilightOnSurfaceVariant,

    error = DarkTwilightError,
    onError = DarkTwilightOnError,
    errorContainer = DarkTwilightError.copy(alpha = 0.1f).compositeOver(DarkTwilightSurface),
    onErrorContainer = DarkTwilightError,

    outline = DarkTwilightOnSurfaceVariant.copy(alpha = 0.5f),
    outlineVariant = DarkTwilightSurfaceVariant,

    inverseSurface = WarmLightSurface,
    inverseOnSurface = WarmLightOnSurface,
    inversePrimary = WarmLightPrimary,

    surfaceTint = DarkTwilightPrimary,
    scrim = Color.Black.copy(alpha = 0.6f)
)

data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val info: Color,
    val onInfo: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color
)

@Composable
fun getExtendedColors(isDark: Boolean): ExtendedColors {
    return if (isDark) {
        ExtendedColors(
            success = DarkTwilightSuccess,
            onSuccess = DarkTwilightOnPrimary,
            warning = DarkTwilightWarning,
            onWarning = DarkTwilightOnPrimary,
            info = DarkTwilightInfo,
            onInfo = DarkTwilightOnPrimary,
            surfaceVariant = DarkTwilightSurfaceVariant,
            onSurfaceVariant = DarkTwilightOnSurfaceVariant
        )
    } else {
        ExtendedColors(
            success = WarmLightSuccess,
            onSuccess = WarmLightOnPrimary,
            warning = WarmLightWarning,
            onWarning = WarmLightOnPrimary,
            info = WarmLightInfo,
            onInfo = WarmLightOnPrimary,
            surfaceVariant = WarmLightSurfaceVariant,
            onSurfaceVariant = WarmLightOnSurfaceVariant
        )
    }
}

val LocalExtendedColors = compositionLocalOf<ExtendedColors> {
    error("No extended colors provided")
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val extendedColors = getExtendedColors(darkTheme)

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}

object AppTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current

    val colorScheme: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val typography: androidx.compose.material3.Typography
        @Composable
        get() = MaterialTheme.typography

    val shapes: androidx.compose.material3.Shapes
        @Composable
        get() = MaterialTheme.shapes
}