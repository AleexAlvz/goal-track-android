package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model

/**
 * Data that represents UI State from SignUp Screen
 * */
data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
)