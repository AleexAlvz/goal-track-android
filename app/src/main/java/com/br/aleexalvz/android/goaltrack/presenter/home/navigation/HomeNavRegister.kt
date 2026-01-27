package com.br.aleexalvz.android.goaltrack.presenter.home.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.HomeScreen

fun NavGraphBuilder.registerFeatureHomeRoutes(navController: NavController) {
    navigation(
        route = HomeRoutes.HOME_GRAPH,
        startDestination = HomeRoutes.HOME_SCREEN
    ) {
        composable(route = HomeRoutes.HOME_SCREEN) { HomeScreen(navController) }
        composable(route = HomeRoutes.ACTIONS) { Text("Welcome to Actions!") }
        composable(route = HomeRoutes.CALENDAR) { Text("Welcome to Calendar!") }
        composable(route = HomeRoutes.FRIENDS) { Text("Welcome to Friends!") }
    }
}