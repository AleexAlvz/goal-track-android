package com.br.aleexalvz.android.goaltrack

import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import com.br.aleexalvz.android.goaltrack.presenter.login.navigation.LoginRoutes

private const val LOADING_ROUTE = "loading"

enum class StartDestination(val route: String) {
    LOADING(LOADING_ROUTE),
    LOGIN(LoginRoutes.LOGIN),
    HOME(HomeRoutes.HOME_SCREEN)
}