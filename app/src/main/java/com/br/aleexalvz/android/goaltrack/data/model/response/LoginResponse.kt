package com.br.aleexalvz.android.goaltrack.data.model.response

import com.br.aleexalvz.android.goaltrack.domain.model.login.Session
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("authToken") val authToken: String,
    @SerialName("email") val email: String,
    @SerialName("fullName") val fullName: String
)

fun LoginResponse.toSession(): Session = Session(
    refreshToken = refreshToken,
    authToken = authToken,
    email = email,
    fullName = fullName
)