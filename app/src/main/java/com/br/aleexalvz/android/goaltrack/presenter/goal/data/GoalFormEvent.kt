package com.br.aleexalvz.android.goaltrack.presenter.goal.data

sealed interface GoalFormEvent {
    data class SubmittedWithSuccess(val goalId: Long) : GoalFormEvent
    data object Deleted : GoalFormEvent
    data object InvalidParams : GoalFormEvent
    data object ConnectionError : GoalFormEvent
    data object UnexpectedError : GoalFormEvent
}