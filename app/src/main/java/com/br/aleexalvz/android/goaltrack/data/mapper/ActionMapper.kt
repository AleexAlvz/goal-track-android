package com.br.aleexalvz.android.goaltrack.data.mapper

import com.br.aleexalvz.android.goaltrack.data.model.dto.ActionDTO
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel

fun ActionModel.toData(): ActionDTO = ActionDTO(
    id = id,
    goalId = goalId,
    title = title,
    description = description.orEmpty(),
    frequency = frequency.name,
    creationDate = creationDate.toString(),
    endDate = endDate?.toString()
)