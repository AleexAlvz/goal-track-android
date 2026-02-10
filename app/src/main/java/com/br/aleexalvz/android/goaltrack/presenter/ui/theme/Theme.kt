package com.br.aleexalvz.android.goaltrack.presenter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightTheme = lightColorScheme(
    primary = LightThemeColors.PrimaryColor,
    onPrimary = LightThemeColors.OnPrimaryColor,
    secondary = LightThemeColors.SecondaryColor,
    onSecondary = LightThemeColors.OnSecondaryColor,
    background = LightThemeColors.Background,
    onBackground = LightThemeColors.OnBackground,
    surfaceContainer = LightThemeColors.SurfaceColor,
    surface = LightThemeColors.SurfaceColor,
    onSurface = LightThemeColors.OnSurfaceColor,
    onSurfaceVariant = LightThemeColors.OnSurfaceVariantColor,
    primaryContainer = LightThemeColors.PrimaryColor,
    onPrimaryContainer = LightThemeColors.OnPrimaryColor,
)

private val DarkTheme = lightColorScheme(
    primary = DarkThemeColors.PrimaryColor,
    onPrimary = DarkThemeColors.OnPrimaryColor,
    secondary = DarkThemeColors.SecondaryColor,
    onSecondary = DarkThemeColors.OnSecondaryColor,
    background = DarkThemeColors.Background,
    onBackground = DarkThemeColors.OnBackground,
    surfaceContainer = DarkThemeColors.SurfaceColor,
    surface = DarkThemeColors.SurfaceColor,
    onSurface = DarkThemeColors.OnSurfaceColor,
    onSurfaceVariant = DarkThemeColors.OnSurfaceVariantColor,
    primaryContainer = DarkThemeColors.PrimaryColor,
    onPrimaryContainer = DarkThemeColors.OnPrimaryColor
)

@Composable
fun GoalTrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkTheme
        else -> LightTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}