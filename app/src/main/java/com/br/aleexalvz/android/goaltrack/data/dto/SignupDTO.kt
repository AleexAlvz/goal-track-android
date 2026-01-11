package com.br.aleexalvz.android.goaltrack.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignupDTO(
    val email: String,
    val password: String
)
