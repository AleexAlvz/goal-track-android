package com.br.aleexalvz.android.goaltrack.presenter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GoalBadge() {
    BadgedBox(
        modifier = Modifier.fillMaxWidth(),
        badge = {
//            if (itemCount > 0) {
                Badge(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ) {
                    Text("Texto")
                }
//            }
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Shopping cart",
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun GoalBadgePreview(){
    GoalBadge()
}