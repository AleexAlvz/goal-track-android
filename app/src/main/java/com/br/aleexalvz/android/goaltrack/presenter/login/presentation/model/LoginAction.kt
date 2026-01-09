package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

sealed class LoginAction {
    data class UpdateEmail(val email: String): LoginAction()
    data class UpdatePassword(val password: String): LoginAction()
    data class UpdateRememberMeCheckBox(val rememberMe: Boolean): LoginAction()
    data class Login(val email: String, val password: String): LoginAction()
}
