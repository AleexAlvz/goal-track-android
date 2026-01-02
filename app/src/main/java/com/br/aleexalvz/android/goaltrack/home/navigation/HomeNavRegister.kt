package com.br.aleexalvz.android.goaltrack.home.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.registerFeatureHomeRoutes(navController: NavController) {
    navigation(
        route = HomeRoutes.FEATURE_HOME,
        startDestination = HomeRoutes.HOME
    ) {
        composable(route = HomeRoutes.HOME) { Text("Welcome to home!") }
    }
}