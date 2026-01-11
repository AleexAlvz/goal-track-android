package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

sealed interface LoginAction {
    data class UpdateEmail(val email: String) : LoginAction
    data class UpdatePassword(val password: String) : LoginAction
    data class UpdateRememberMeCheckBox(val rememberMe: Boolean) : LoginAction
    data object Submit : LoginAction
    data object LoginWithGoogle : LoginAction
}
