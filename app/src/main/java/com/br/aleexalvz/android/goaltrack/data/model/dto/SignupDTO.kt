package com.br.aleexalvz.android.goaltrack.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignupDTO(
    val email: String,
    val fullName: String,
    val password: String,
    val confirmPassword: String,
)
