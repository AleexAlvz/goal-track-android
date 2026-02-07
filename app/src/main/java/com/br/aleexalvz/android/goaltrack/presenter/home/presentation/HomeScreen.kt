package com.br.aleexalvz.android.goaltrack.presenter.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.model.HomeState
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState: HomeState by homeViewModel.state.collectAsStateWithLifecycle()
    homeViewModel.getHome()

    HomeContent(
        homeState = homeState,
        onNavigateToGoals = {
            navController.navigate(HomeRoutes.GOALS)
        },
        onNavigateToCreateGoal = {
            navController.navigate(HomeRoutes.CREATE_GOAL)
        }
    )
}

@Composable
fun HomeContent(
    homeState: HomeState,
    onNavigateToGoals: () -> Unit,
    onNavigateToCreateGoal: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        HomeHeader()
        GoalStatusCard(
            modifier = Modifier.padding(16.dp),
            state = homeState.goalStatusCardState,
            onNavigateToGoals = onNavigateToGoals,
            onNavigateToCreateGoal = onNavigateToCreateGoal
        )
    }
}