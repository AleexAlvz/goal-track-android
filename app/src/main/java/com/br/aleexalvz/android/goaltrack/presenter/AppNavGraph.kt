package com.br.aleexalvz.android.goaltrack.presenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.br.aleexalvz.android.goaltrack.StartDestination
import com.br.aleexalvz.android.goaltrack.presenter.components.AppNavigationBar
import com.br.aleexalvz.android.goaltrack.presenter.components.BottomNavItem
import com.br.aleexalvz.android.goaltrack.presenter.goal.ui.GoalDetailScreen
import com.br.aleexalvz.android.goaltrack.presenter.goal.ui.GoalFormScreen
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes.GOAL_ID_ARG
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.registerFeatureHomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.LoginRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.registerFeatureLoginRoutes

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel
) {
    val currentScreen: MutableState<String> = remember {
        mutableStateOf(navController.currentDestination?.route.toString())
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let { currentScreen.value = it }
    }

    val bottomNavigationItems = listOf(
        BottomNavItem("Home", HomeRoutes.HOME_SCREEN, Icons.Filled.Home),
        BottomNavItem("Goals", HomeRoutes.GOALS, Icons.Filled.TaskAlt),
        BottomNavItem("Calendar", HomeRoutes.CALENDAR, Icons.Filled.Flag),
        BottomNavItem("Settings", HomeRoutes.SETTINGS, Icons.Filled.Settings),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowNavigationBar(currentScreen.value)) {
                AppNavigationBar(navController, bottomNavigationItems, HomeRoutes.HOME_GRAPH)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = StartDestination.LOADING.route
            ) {
                composable(route = StartDestination.LOADING.route) {
                    LoadingScreen(navController, mainViewModel)
                }
                registerFeatureLoginRoutes(navController)
                registerFeatureHomeRoutes(navController)

                composable(
                    route = HomeRoutes.GOAL_FORM_FULL_ROUTE,
                    arguments = listOf(
                        navArgument(GOAL_ID_ARG) {
                            type = NavType.LongType
                            defaultValue = -1L
                        }
                    )
                ) {
                    GoalFormScreen(navController)
                }

                composable(
                    route = HomeRoutes.GOAL_DETAIL_FULL_ROUTE,
                    arguments = listOf(
                        navArgument(GOAL_ID_ARG) {
                            type = NavType.LongType
                            defaultValue = -1L
                        }
                    )
                ) {
                    GoalDetailScreen(navController)
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val startDestinationState by mainViewModel.startDestination.collectAsState()

    LaunchedEffect(startDestinationState) {
        when (startDestinationState) {
            StartDestination.LOGIN.route -> {
                navController.navigate(LoginRoutes.LOGIN) {
                    popUpTo(StartDestination.LOADING.route) { inclusive = true }
                }
            }

            StartDestination.HOME.route -> {
                navController.navigate(HomeRoutes.HOME_SCREEN) {
                    popUpTo(StartDestination.LOADING.route) { inclusive = true }
                }
            }

            StartDestination.LOADING.route -> Unit
        }
    }
}

private fun shouldShowNavigationBar(currentRoute: String): Boolean {
    val notAllowedRoutesToShowNavigationBar = listOf(
        LoginRoutes.LOGIN,
        LoginRoutes.SIGN_UP,
        LoginRoutes.RECOVERY_PASSWORD,
        StartDestination.LOADING.route
    )

    return currentRoute.isNotBlank() && !notAllowedRoutesToShowNavigationBar.contains(currentRoute)
}