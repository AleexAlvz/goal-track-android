package com.br.aleexalvz.android.goaltrack.presenter.home.presentation.goal.model

import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel

data class GoalState(
    val goals: List<GoalModel> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)