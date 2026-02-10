package com.br.aleexalvz.android.goaltrack.data.repository.remote

import com.br.aleexalvz.android.goaltrack.core.common.JsonHelper
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.mapper.toData
import com.br.aleexalvz.android.goaltrack.data.model.GoalEndpoints
import com.br.aleexalvz.android.goaltrack.data.model.dto.GoalDTO
import com.br.aleexalvz.android.goaltrack.data.model.response.GoalResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.GoalsResponse
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum
import com.br.aleexalvz.android.goaltrack.domain.repository.GoalRepository
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val networkProvider: NetworkProvider
) : GoalRepository {

    override suspend fun getGoals(filterStatus: GoalStatusEnum?): NetworkResponse<GoalsResponse> {
        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = GoalEndpoints.GOALS_ENDPOINT,
                method = NetworkMethod.GET
            ),
            responseSerializer = GoalsResponse.serializer(),
        )
    }

    override suspend fun createGoal(goal: GoalModel): NetworkResponse<GoalResponse> {
        val body = goal.toData()
        val jsonBody = JsonHelper.toJson(body, GoalDTO.serializer())

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = GoalEndpoints.GOALS_ENDPOINT,
                method = NetworkMethod.POST,
                bodyJson = jsonBody
            ),
            responseSerializer = GoalResponse.serializer(),
        )
    }

    override suspend fun getGoalById(goalId: Long): NetworkResponse<GoalResponse> {
        val getGoalByIdEndpoint =
            GoalEndpoints.GET_GOAL_BY_ID.replace("{goalId}", goalId.toString())

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = getGoalByIdEndpoint,
                method = NetworkMethod.GET
            ),
            responseSerializer = GoalResponse.serializer(),
        )
    }

    override suspend fun updateGoal(goal: GoalModel): NetworkResponse<GoalResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getGoalByStatus(status: GoalStatusEnum): NetworkResponse<List<GoalResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGoalById(id: Long): NetworkResponse<Unit> {
        TODO("Not yet implemented")
    }
}