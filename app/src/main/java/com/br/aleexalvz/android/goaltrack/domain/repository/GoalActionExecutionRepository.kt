package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.domain.model.ActionExecutionModel
import java.time.YearMonth

interface GoalActionExecutionRepository {
    suspend fun registerExecution(execution: ActionExecutionModel)
    suspend fun deleteExecutionById(id: Long)
    suspend fun getExecutionsByMonth(month: YearMonth): List<ActionExecutionModel>
    suspend fun getExecutionsByAction(actionId: Long): List<ActionExecutionModel>
}