package com.br.aleexalvz.android.goaltrack.presenter.goal.data

import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.SkillModel

data class GoalFormState(
    val id: Long = -1L,
    val title: String = "",
    val description: String = "",
    val category: GoalCategoryEnum? = null,
    val skills: List<SkillModel> = emptyList(),
    val titleError: String? = null,
    val descriptionError: String? = null,
    val categoryError: String? = null,
    val isLoading: Boolean = false
)