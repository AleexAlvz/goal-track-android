package com.br.aleexalvz.android.goaltrack.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SignupModel(
    val email: String,
    val fullName: String,
    val password: String,
    val confirmPassword: String
)
