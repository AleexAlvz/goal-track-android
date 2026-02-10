package com.br.aleexalvz.android.goaltrack.data.model.response

import kotlinx.serialization.Serializable

@Serializable
class GoalsResponse(
    val goals: List<GoalResponse>
)