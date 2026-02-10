package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.GoalResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.GoalsResponse
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum

interface GoalRepository {

    suspend fun getGoals(filterStatus: GoalStatusEnum? = null): NetworkResponse<GoalsResponse>
    suspend fun createGoal(goal: GoalModel): NetworkResponse<GoalResponse>
    suspend fun updateGoal(goal: GoalModel): NetworkResponse<GoalResponse>
    suspend fun getGoalByStatus(status: GoalStatusEnum): NetworkResponse<List<GoalResponse>>
    suspend fun getGoalById(goalId: Long): NetworkResponse<GoalResponse>
    suspend fun deleteGoalById(id: Long): NetworkResponse<Unit>
}