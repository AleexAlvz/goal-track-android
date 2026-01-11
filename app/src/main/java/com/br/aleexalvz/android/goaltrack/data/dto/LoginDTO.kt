package com.br.aleexalvz.android.goaltrack.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    val email: String,
    val password: String
)
