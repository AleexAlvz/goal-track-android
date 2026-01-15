package com.br.aleexalvz.android.goaltrack.presenter.home.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.registerFeatureHomeRoutes(navController: NavController) {
    navigation(
        route = HomeRoutes.Home,
        startDestination = HomeRoutes.Goals
    ) {
        composable(route = HomeRoutes.Goals) { Text("Welcome to Goals!") }
        composable(route = HomeRoutes.Actions) { Text("Welcome to Actions!") }
        composable(route = HomeRoutes.Calendar) { Text("Welcome to Calendar!") }
        composable(route = HomeRoutes.Friends) { Text("Welcome to Friends!") }
    }
}