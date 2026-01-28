package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

sealed interface SignUpAction {
    data object Submit : SignUpAction
    data class UpdateEmail(val email: String) : SignUpAction
    data class UpdateFullName(val fullName: String) : SignUpAction
    data class UpdatePassword(val password: String) : SignUpAction
    data class UpdateConfirmPassword(val confirmPassword: String) : SignUpAction
}
