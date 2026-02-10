package com.br.aleexalvz.android.goaltrack.presenter.goal.data

import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel

data class GoalDetailState(
    val goal: GoalModel? = null,
    val isLoading: Boolean = false,
    val errorToFetch: Boolean = false
)