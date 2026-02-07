package com.br.aleexalvz.android.goaltrack.presenter.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        IconButton(
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically),
            colors = IconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile"
            )
        }


        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = "Bem vindo,"
            )

            Text(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                text = "Alex Alves"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {
    HomeHeader(Modifier)
}