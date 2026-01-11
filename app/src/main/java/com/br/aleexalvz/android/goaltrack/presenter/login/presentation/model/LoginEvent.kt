package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent
    data object InvalidCredentials : LoginEvent
    data object ConnectionError : LoginEvent
    data object UnexpectedError : LoginEvent
}