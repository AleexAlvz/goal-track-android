package com.br.aleexalvz.android.goaltrack.presenter.action.model

sealed interface ActionFormEvent {
    data object Created : ActionFormEvent
    data object InvalidParams : ActionFormEvent
    data object ConnectionError : ActionFormEvent
    data object UnexpectedError : ActionFormEvent
}