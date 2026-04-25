package com.br.aleexalvz.android.goaltrack.presenter.action.model

sealed interface ActionFormEvent {
    data class Created(val actionId: Long) : ActionFormEvent
    data object Deleted : ActionFormEvent
    data object InvalidParams : ActionFormEvent
    data object ConnectionError : ActionFormEvent
    data object UnexpectedError : ActionFormEvent
}
