package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.domain.model.GoalActionModel

interface GoalActionRepository {
    suspend fun getActionsByGoal(goalId: Long): List<GoalActionModel>
    suspend fun getActionById(id: Long): GoalActionModel?
    suspend fun createAction(action: GoalActionModel)
    suspend fun updateAction(action: GoalActionModel)
    suspend fun deleteActionById(id: Long)
}