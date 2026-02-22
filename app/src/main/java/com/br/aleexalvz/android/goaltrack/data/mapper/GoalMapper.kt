package com.br.aleexalvz.android.goaltrack.data.mapper

import com.br.aleexalvz.android.goaltrack.data.model.dto.GoalDTO
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel

fun GoalModel.toData() = GoalDTO(
    id = id,
    title = title,
    description = description,
    category = category.name,
    skills = skills.map { it.name },
    creationDate = creationDate.toString(),
    endDate = endDate?.toString(),
    status = status.name
)