package com.br.aleexalvz.android.goaltrack.presenter.ui.theme

import androidx.compose.ui.graphics.Color

object LightThemeColors {
    val Background = Color(0xFFF5F0F0)
    val OnBackground = Color.Black

    val PrimaryColor = Color(0xFF0D80F2)
    val OnPrimaryColor = Color.White

    val SecondaryColor = Color.White
    val OnSecondaryColor = Color.Black

    val OnPrimaryContainer = PrimaryColor
    val PrimaryContainer = PrimaryColor.copy(alpha = 0.3f)

    val SurfaceColor = Color.White
    val OnSurfaceColor = Color.Black
    val OnSurfaceVariantColor = Color(0xFF4A739C)

    val ErrorColor = Color.Red
    val ErrorContainerColor = SecondaryColor
}

object DarkThemeColors {
    val Background = Color(0xFF0B1218)
    val OnBackground = Color.White

    val PrimaryColor = Color(0xFF007BFF)
    val OnPrimaryColor = Color.White

    val SecondaryColor = Color(0xFF242C3D)
    val OnSecondaryColor = Color.White

    val OnPrimaryContainer = PrimaryColor
    val PrimaryContainer = PrimaryColor.copy(alpha = 0.3f)

    val SurfaceColor = Color(0xFF242C3D)
    val OnSurfaceColor = Color.White
    val OnSurfaceVariantColor = Color(0xFF4A739C)

    val ErrorColor = Color(0xFFC21010)
    val ErrorContainerColor = SecondaryColor
}
