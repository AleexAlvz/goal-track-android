package com.br.aleexalvz.android.goaltrack.presenter.home.navigation

object HomeRoutes {
    const val HOME_GRAPH = "home-graph"
    const val HOME_SCREEN = "home"
    const val GOALS = "goals"

    const val ACTIONS = "actions"
    const val CALENDAR = "calendar"
    const val FRIENDS = "friends"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"

    //Goals
    const val GOAL_FORM = "goal_form"
    const val GOAL_DETAIL = "goal_detail"
    const val GOAL_ID_ARG = "goalId"

    //Goals Route with params definition
    const val GOAL_FORM_FULL_ROUTE = "$GOAL_FORM?$GOAL_ID_ARG={$GOAL_ID_ARG}"
    const val GOAL_DETAIL_FULL_ROUTE = "$GOAL_DETAIL?$GOAL_ID_ARG={$GOAL_ID_ARG}"

    //Helper to create Goals Routes with params
    fun goalFormWithId(goalId: Long) = "$GOAL_FORM?$GOAL_ID_ARG=$goalId"
    fun goalDetailWithId(goalId: Long) = "$GOAL_DETAIL?$GOAL_ID_ARG=$goalId"
}