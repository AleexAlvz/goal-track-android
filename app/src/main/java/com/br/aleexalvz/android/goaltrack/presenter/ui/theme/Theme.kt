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
    surface = LightThemeColors.SurfaceColor,
    onSurface = LightThemeColors.OnSurfaceColor,
    onSurfaceVariant = LightThemeColors.OnSurfaceVariantColor
)

private val DarkTheme = lightColorScheme(
    primary = DarkThemeColors.PrimaryColor,
    onPrimary = DarkThemeColors.OnPrimaryColor,
    secondary = DarkThemeColors.SecondaryColor,
    onSecondary = DarkThemeColors.OnSecondaryColor,
    background = DarkThemeColors.Background,
    onBackground = DarkThemeColors.OnBackground,
    surface = DarkThemeColors.SurfaceColor,
    onSurface = DarkThemeColors.OnSurfaceColor,
    onSurfaceVariant = DarkThemeColors.OnSurfaceVariantColor
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