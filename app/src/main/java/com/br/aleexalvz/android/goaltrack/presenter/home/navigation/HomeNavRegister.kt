package com.br.aleexalvz.android.goaltrack.presenter.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.calendar.CalendarScreen
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.goal.ui.GoalsScreen
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.home.ui.HomeScreen
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.settings.SettingsScreen

fun NavGraphBuilder.registerFeatureHomeRoutes(navController: NavController) {
    navigation(
        route = HomeRoutes.HOME_GRAPH,
        startDestination = HomeRoutes.HOME_SCREEN
    ) {
        composable(route = HomeRoutes.HOME_SCREEN) { HomeScreen(navController) }
        composable(route = HomeRoutes.GOALS) { GoalsScreen(navController) }
        composable(route = HomeRoutes.CALENDAR) { CalendarScreen() }
        composable(route = HomeRoutes.SETTINGS) { SettingsScreen() }
    }
}