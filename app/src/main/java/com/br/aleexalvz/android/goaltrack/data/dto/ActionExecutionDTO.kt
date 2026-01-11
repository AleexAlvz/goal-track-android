package com.br.aleexalvz.android.goaltrack.data.dto

data class ActionExecutionDTO(
    val id: Long,
    val actionId: Long,
    val executionDate: String,
    val notes: String? = null
)