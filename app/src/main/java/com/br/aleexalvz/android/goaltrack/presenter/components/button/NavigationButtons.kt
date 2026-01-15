package com.br.aleexalvz.android.goaltrack.presenter.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun BackNavigationButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "back button",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun LogoutNavigationButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = "logout button",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}