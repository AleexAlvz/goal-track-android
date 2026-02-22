package com.br.aleexalvz.android.goaltrack.data.model.response

import com.br.aleexalvz.android.goaltrack.domain.model.goal.SkillModel
import kotlinx.serialization.Serializable

@Serializable
data class SkillResponse(
    val name: String
)

fun SkillResponse.toSkillModel() = SkillModel(
    name = name
)
