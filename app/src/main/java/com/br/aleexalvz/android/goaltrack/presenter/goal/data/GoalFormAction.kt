package com.br.aleexalvz.android.goaltrack.presenter.goal.data

import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum

sealed interface GoalFormAction {
    data class UpdateTitle(val title: String) : GoalFormAction
    data class UpdateDescription(val description: String) : GoalFormAction
    data class UpdateCategory(val category: GoalCategoryEnum) : GoalFormAction
    data class UpdateSkills(val skills: List<String>) : GoalFormAction

    data object Submit : GoalFormAction
}