package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.domain.model.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.GoalStatusEnum

interface GoalRepository {

    suspend fun getGoals(filterStatus: GoalStatusEnum? = null): List<GoalModel>
    suspend fun createGoal(goal: GoalModel)
    suspend fun updateGoal(goal: GoalModel)
    suspend fun getGoalByStatus(status: GoalStatusEnum): List<GoalModel>
    suspend fun deleteGoalById(id: Long)
}