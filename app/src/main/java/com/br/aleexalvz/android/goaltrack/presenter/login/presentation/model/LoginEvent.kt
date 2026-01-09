package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

sealed interface LoginEvent {
    data object OnSuccess : LoginEvent
    data class OnFailure(val throwable: Throwable) : LoginEvent
}