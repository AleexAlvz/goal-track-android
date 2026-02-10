package com.br.aleexalvz.android.goaltrack.presenter.goal.data

sealed interface CreateGoalEvent {
    data object Created : CreateGoalEvent
    data object InvalidParams : CreateGoalEvent
    data object ConnectionError : CreateGoalEvent
    data object UnexpectedError : CreateGoalEvent
}