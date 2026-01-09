package com.br.aleexalvz.android.goaltrack.presenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.br.aleexalvz.android.goaltrack.presenter.components.BottomNavItem
import com.br.aleexalvz.android.goaltrack.presenter.components.NavigationBar
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.registerFeatureHomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.LoginRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.registerFeatureLoginRoutes

@Composable
fun AppNavGraph() {

    val navController: NavHostController = rememberNavController()
    val currentScreen: MutableState<String> = remember {
        mutableStateOf(navController.currentDestination?.route.toString())
    }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let { currentScreen.value = it }
    }

    val bottomNavigationItems = listOf(
        BottomNavItem("Home", HomeRoutes.HOME, Icons.Filled.Home),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowNavigationBar(currentScreen.value)) {
                NavigationBar(navController, bottomNavigationItems, HomeRoutes.HOME)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = LoginRoutes.LOGIN
            ){
                registerFeatureLoginRoutes(navController)
                registerFeatureHomeRoutes(navController)
            }
        }
    }
}

private fun shouldShowNavigationBar(currentRoute: String): Boolean {
    val notAllowedRoutesToShowNavigationBar = listOf(
        LoginRoutes.LOGIN,
        LoginRoutes.SIGN_UP,
        LoginRoutes.RECOVERY_PASSWORD
    )

    return currentRoute.isNotBlank() && !notAllowedRoutesToShowNavigationBar.contains(currentRoute)
}