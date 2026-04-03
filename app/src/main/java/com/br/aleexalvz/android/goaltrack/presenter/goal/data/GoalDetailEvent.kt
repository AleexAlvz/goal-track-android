package com.br.aleexalvz.android.goaltrack.presenter.goal.data

sealed interface GoalDetailEvent {
    data object Completed : GoalDetailEvent
    data object ConnectionError : GoalDetailEvent
    data object UnexpectedError : GoalDetailEvent
}