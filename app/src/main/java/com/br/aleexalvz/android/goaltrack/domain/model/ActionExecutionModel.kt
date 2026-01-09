package com.br.aleexalvz.android.goaltrack.domain.model

import java.time.LocalDate

data class ActionExecutionModel(
    val id: Long,
    val actionId: Long,
    val executionDate: LocalDate,
    val notes: String? = null
)