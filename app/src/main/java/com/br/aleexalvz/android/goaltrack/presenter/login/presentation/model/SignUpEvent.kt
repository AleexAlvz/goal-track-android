package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

sealed interface SignUpEvent {
    data object OnSignUpSuccess : SignUpEvent
    data object InvalidCredentials : SignUpEvent
    data object ConnectionError : SignUpEvent
    data object UnexpectedError : SignUpEvent
}