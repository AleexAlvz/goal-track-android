package com.br.aleexalvz.android.goaltrack.domain.model

import java.time.LocalDate

data class GoalModel(
    val id: Long,
    val title: String,
    val description: String? = "",
    val category: String,
    val creationDate: LocalDate,
    val endDate: LocalDate? = null,
    val status: GoalStatusEnum
)