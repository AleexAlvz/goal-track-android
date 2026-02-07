package com.br.aleexalvz.android.goaltrack.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    @SerialName("goalStatusCard") val goalStatusCard: GoalCardStatus
)

@Serializable
data class GoalCardStatus(
    @SerialName("goalsInProgress") val goalsInProgress: Int
)