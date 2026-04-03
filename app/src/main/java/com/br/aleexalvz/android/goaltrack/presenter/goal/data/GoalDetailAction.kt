package com.br.aleexalvz.android.goaltrack.presenter.goal.data

sealed interface GoalDetailAction {
    data object CompleteGoal : GoalDetailAction
}