package com.br.aleexalvz.android.goaltrack.presenter.components.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.aleexalvz.android.goaltrack.presenter.ui.theme.GoalTrackTheme

data class FabMenuItem(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun FabMenu(
    modifier: Modifier,
    items: List<FabMenuItem>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.End
    ) {

        items.forEachIndexed { index, item ->

            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 250,
                        delayMillis = index * 40
                    )
                ) + fadeIn(),
                exit = fadeOut()
            ) {

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 3.dp,
                    modifier = Modifier.clickable {
                        expanded = false
                        item.onClick()
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            horizontal = 12.dp, vertical = 6.dp
                        )
                    ) {
                        Text(text = item.label)

                        Spacer(Modifier.width(8.dp))

                        Icon(imageVector = item.icon, contentDescription = item.label)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Menu,
                contentDescription = "Menu"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FabMenuPreview() {
    GoalTrackTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            FabMenu(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                items = listOf(
                    FabMenuItem(
                        icon = Icons.Default.Edit,
                        label = "Editar",
                        onClick = {}
                    ),
                    FabMenuItem(
                        icon = Icons.Default.Check,
                        label = "Completar",
                        onClick = {}
                    ),
                    FabMenuItem(
                        icon = Icons.Default.Delete,
                        label = "Excluir",
                        onClick = {}
                    )
                )
            )
        }
    }
}
