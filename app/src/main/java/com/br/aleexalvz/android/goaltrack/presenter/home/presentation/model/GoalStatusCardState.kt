package com.br.aleexalvz.android.goaltrack.presenter.home.presentation.model

sealed interface GoalStatusCardState {
    data object Loading: GoalStatusCardState
    data class InProgress(val goals: Int): GoalStatusCardState
    data object NotInProgress: GoalStatusCardState
}