package com.br.aleexalvz.android.goaltrack.presenter.action.model

sealed interface ActionDetailAction {
    data class addNote(val note: String) : ActionDetailAction
}