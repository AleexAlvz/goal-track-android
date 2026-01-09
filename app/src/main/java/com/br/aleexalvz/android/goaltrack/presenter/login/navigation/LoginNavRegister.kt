package com.br.aleexalvz.android.goaltrack.presenter.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.ui.LoginScreen
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.ui.SignUpScreen

fun NavGraphBuilder.registerFeatureLoginRoutes(navController: NavController){
    composable(route = LoginRoutes.LOGIN) { LoginScreen(navController = navController) }
    composable(route = LoginRoutes.SIGN_UP) { SignUpScreen(navController = navController) }
}