package com.br.aleexalvz.android.goaltrack.presenter.components

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun AppNavigationBar(
    navController: NavController,
    items: List<BottomNavItem>,
    popUpToRoute: String? = null
) {

    val backStackEntry = navController.currentBackStackEntryAsState()

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true)
@Composable
fun NavigationBarPreview() {
    val bottomNavigationItems = listOf(
        BottomNavItem("Goals", HomeRoutes.HOME_SCREEN, Icons.Outlined.TaskAlt),
        BottomNavItem("Actions", HomeRoutes.ACTIONS, Icons.Outlined.Home),
        BottomNavItem("Calendar", HomeRoutes.CALENDAR, Icons.Outlined.CalendarMonth),
        BottomNavItem("Friends", HomeRoutes.FRIENDS, Icons.Outlined.Construction),
    )
    Scaffold(bottomBar = {
        AppNavigationBar(
            navController = NavController(LocalContext.current),
            items = bottomNavigationItems,
            popUpToRoute = null
        )
    }) {}

}
