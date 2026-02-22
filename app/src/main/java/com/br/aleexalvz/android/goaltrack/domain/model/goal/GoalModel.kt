package com.br.aleexalvz.android.goaltrack.domain.model.goal

import java.time.LocalDate

data class GoalModel(
    val id: Long = 0,
    val title: String,
    val description: String? = "",
    val category: GoalCategoryEnum,
    val skills: List<SkillModel> = emptyList(),
    val creationDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = null,
    val status: GoalStatusEnum = GoalStatusEnum.IN_PROGRESS
)