package com.br.aleexalvz.android.goaltrack.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("Bearer") val token: String
)