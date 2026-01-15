package com.br.aleexalvz.android.goaltrack.data.model.dto

data class ActionExecutionDTO(
    val id: Long,
    val actionId: Long,
    val executionDate: String,
    val notes: String? = null
)