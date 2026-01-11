package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

sealed class SignUpAction {
    data class UpdateEmail(val email: String): SignUpAction()
    data class UpdatePassword(val password: String): SignUpAction()
    data class UpdateConfirmPassword(val confirmPassword: String): SignUpAction()
}
