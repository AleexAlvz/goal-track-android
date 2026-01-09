package com.br.aleexalvz.android.goaltrack.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val email: String,
    val password: String
)
