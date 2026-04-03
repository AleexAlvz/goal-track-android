package com.br.aleexalvz.android.goaltrack.presenter.action.model

sealed interface ActionDetailEvent {
    data object Deleted : ActionDetailEvent
    data object ConnectionError : ActionDetailEvent
    data object UnexpectedError : ActionDetailEvent
}