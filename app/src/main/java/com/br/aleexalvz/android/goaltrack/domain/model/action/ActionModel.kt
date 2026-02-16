package com.br.aleexalvz.android.goaltrack.domain.model.action

import java.time.LocalDate

data class ActionModel(
    val id: Long = 0,
    val goalId: Long,
    val title: String = "",
    val description: String? = "",
    val frequency: ActionFrequencyEnum = ActionFrequencyEnum.ONCE,
    val creationDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = null
)