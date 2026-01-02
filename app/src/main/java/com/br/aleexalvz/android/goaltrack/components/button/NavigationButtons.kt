package com.aleexalvz.designsystem.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.br.aleexalvz.android.goaltrack.ui.theme.GrayLight

@Composable
fun BackNavigationButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "back button",
            tint = Color.White
        )
    }
}

@Composable
fun LogoutNavigationButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonColors(
            containerColor = GrayLight,
            contentColor = Color.White,
            disabledContainerColor = GrayLight,
            disabledContentColor = Color.White
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = "logout button",
            tint = Color.White
        )
    }
}