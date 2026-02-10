package com.br.aleexalvz.android.goaltrack.presenter.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.background
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
@Preview(
    name = "Light",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun NavigationBarPreview() {
    val bottomNavigationItems = listOf(
        BottomNavItem("Home", HomeRoutes.HOME_SCREEN, Icons.Filled.Home),
        BottomNavItem("Goals", HomeRoutes.GOALS, Icons.Filled.Flag),
        BottomNavItem("Calendar", HomeRoutes.CALENDAR, Icons.Filled.CalendarMonth),
        BottomNavItem("Settings", HomeRoutes.SETTINGS, Icons.Filled.Settings)
    )
    Scaffold(bottomBar = {
        AppNavigationBar(
            navController = NavController(LocalContext.current),
            items = bottomNavigationItems,
            popUpToRoute = null
        )
    }) {}
}
