package com.br.aleexalvz.android.goaltrack.domain.model.login

data class Session(
    val refreshToken: String,
    val authToken: String? = null,
    val email: String,
    val fullName: String
)