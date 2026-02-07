package com.br.aleexalvz.android.goaltrack.domain.model.action

import java.time.LocalDate

data class GoalActionModel(
    private val id: Long,
    private val goalId: Long,
    private val title: String,
    private val description: String,
    private val frequency: ActionFrequencyEnum,
    private val creationDate: LocalDate,
    private val endDate: LocalDate? = null
)