package com.br.aleexalvz.android.goaltrack.presenter.goal.data

import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum

sealed interface CreateGoalAction {
    data class UpdateTitle(val title: String) : CreateGoalAction
    data class UpdateDescription(val description: String) : CreateGoalAction
    data class UpdateCategory(val category: GoalCategoryEnum) : CreateGoalAction
    data object Submit : CreateGoalAction
}