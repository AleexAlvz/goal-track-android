package com.br.aleexalvz.android.goaltrack.data.model.response

import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.model.action.toActionFrequencyEnum
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ActionResponse(
    val id: Long,
    val goalId: Long,
    val title: String,
    val description: String?,
    val frequency: String,
    val creationDate: String,
    val endDate: String?
)

fun ActionResponse.toActionModel() = ActionModel(
    id = id,
    goalId = goalId,
    title = title,
    description = description,
    frequency = frequency.toActionFrequencyEnum(),
    creationDate = LocalDate.parse(creationDate),
    endDate = endDate?.let { LocalDate.parse(endDate) },
)


