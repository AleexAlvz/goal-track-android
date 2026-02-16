package com.br.aleexalvz.android.goaltrack.presenter.action.model

import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum

data class ActionFormState(
    val id: Long = -1L,
    val goalId: Long = -1L,
    val title: String = "",
    val description: String? = "",
    val frequency: ActionFrequencyEnum = ActionFrequencyEnum.ONCE,
    val titleError: String? = null,
    val descriptionError: String? = null,
    val isLoading: Boolean = false
)