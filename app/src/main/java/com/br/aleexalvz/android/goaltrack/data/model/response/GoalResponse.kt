package com.br.aleexalvz.android.goaltrack.data.model.response

import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.toGoalCategoryEnum
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GoalResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val category: String,
    val creationDate: String,
    val endDate: String?,
    val status: String,
    val skills: List<SkillResponse>
)

fun GoalResponse.toGoalModel() = GoalModel(
    id = id,
    title = title,
    description = description,
    category = category.toGoalCategoryEnum(),
    creationDate = LocalDate.parse(creationDate),
    endDate = endDate?.let { LocalDate.parse(endDate) },
    status = GoalStatusEnum.entries.first { it.name == status },
    skills = skills.map { it.toSkillModel() }
)


