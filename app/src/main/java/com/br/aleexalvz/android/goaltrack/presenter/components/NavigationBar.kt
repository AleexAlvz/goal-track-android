package com.br.aleexalvz.android.goaltrack.presenter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun NavigationBar(
    navController: NavController,
    items: List<BottomNavItem>,
    popUpToRoute: String? = null
) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    HorizontalDivider(
        Modifier
            .fillMaxWidth()
            .height(1.dp))

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.onPrimary,
                ),
                selected = item.route == backStackEntry.value?.destination?.route,
                onClick = {
                    if (item.route != backStackEntry.value?.destination?.route) {
                        navController.navigate(route = item.route) {
                            popUpToRoute?.let { popUpTo(it) }
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = "${item.name} Icon") },
                label = { Text(text = item.name) }
            )
        }
    }
}
