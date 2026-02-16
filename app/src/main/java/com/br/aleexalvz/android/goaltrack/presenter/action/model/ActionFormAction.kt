package com.br.aleexalvz.android.goaltrack.presenter.action.model

import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum

sealed interface ActionFormAction {
    data class UpdateTitle(val title: String) : ActionFormAction
    data class UpdateDescription(val description: String) : ActionFormAction
    data class UpdateFrequency(val frequency: ActionFrequencyEnum) : ActionFormAction
    data object Submit : ActionFormAction
}