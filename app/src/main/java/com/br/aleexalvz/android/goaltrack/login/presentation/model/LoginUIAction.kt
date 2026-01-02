package com.br.aleexalvz.android.goaltrack.login.presentation.model

sealed class LoginUIAction {
    data class UpdateEmail(val email: String): LoginUIAction()
    data class UpdatePassword(val password: String): LoginUIAction()
    data class UpdateRememberMeCheckBox(val rememberMe: Boolean): LoginUIAction()
    data class Login(val email: String, val password: String): LoginUIAction()
}
