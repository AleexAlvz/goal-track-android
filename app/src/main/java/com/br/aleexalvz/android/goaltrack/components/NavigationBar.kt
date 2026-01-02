package com.br.aleexalvz.android.goaltrack.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    indicatorColor = Color.Cyan,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White
                ),
                selected = item.route == backStackEntry.value?.destination?.route,
                onClick = {
                    if (item.route != backStackEntry.value?.destination?.route){
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
