package com.br.aleexalvz.android.goaltrack.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val email: String,
    val password: String
)
