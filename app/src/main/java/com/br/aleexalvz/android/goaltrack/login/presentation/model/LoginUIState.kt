package com.aleexalvz.login.presentation.model

/**
 * Data that represents UI State from Login Screen
 * */
data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
)