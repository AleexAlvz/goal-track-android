package com.br.aleexalvz.android.goaltrack.presenter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

//private val DarkTheme = darkColorScheme(
//    primary = GrayDefault,
//    secondary = GrayDefault,
//    tertiary = Pink80,
//    background = DarkBackground
//)

private val LightTheme = lightColorScheme(
    primary = LightThemeColors.PrimaryColor,
    onPrimary = LightThemeColors.OnPrimaryColor,
    secondary = LightThemeColors.SecondaryColor,
    onSecondary = LightThemeColors.OnSecondaryColor,
    background = LightThemeColors.Background,
    surface = LightThemeColors.SurfaceColor,
    onSurface = LightThemeColors.OnSurfaceColor,
    onSurfaceVariant = LightThemeColors.OnSurfaceVariantColor
)

@Composable
fun GoalTrackTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(), TODO habilitar dark theme
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

//        darkTheme -> DarkTheme
        else -> LightTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}