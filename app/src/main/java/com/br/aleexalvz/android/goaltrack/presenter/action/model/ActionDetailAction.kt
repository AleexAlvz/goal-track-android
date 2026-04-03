package com.br.aleexalvz.android.goaltrack.presenter.action.model

sealed interface ActionDetailAction {
    data class AddNote(val note: String) : ActionDetailAction
    data object DeleteAction : ActionDetailAction
    data object CompleteAction : ActionDetailAction
}