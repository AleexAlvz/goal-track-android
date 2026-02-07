package com.br.aleexalvz.android.goaltrack.domain.model.login

import kotlinx.serialization.Serializable

@Serializable
data class RecoveryPasswordModel(
    val email: String
)
