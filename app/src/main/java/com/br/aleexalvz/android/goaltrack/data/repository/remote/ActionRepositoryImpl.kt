package com.br.aleexalvz.android.goaltrack.data.repository.remote

import com.br.aleexalvz.android.goaltrack.core.common.JsonHelper
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.mapper.toData
import com.br.aleexalvz.android.goaltrack.data.model.ActionEndpoints
import com.br.aleexalvz.android.goaltrack.data.model.dto.ActionDTO
import com.br.aleexalvz.android.goaltrack.data.model.response.ActionResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.ActionsResponse
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.repository.ActionRepository
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor(
    private val networkProvider: NetworkProvider
) : ActionRepository {
    override suspend fun getActionsByGoal(goalId: Long): NetworkResponse<ActionsResponse> {
        val endpoint = ActionEndpoints.ACTIONS_ENDPOINT + "/goal/$goalId"

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = endpoint,
                method = NetworkMethod.GET
            ),
            responseSerializer = ActionsResponse.serializer(),
        )
    }

    override suspend fun getActionById(id: Long): NetworkResponse<ActionResponse> {
        val endpoint = ActionEndpoints.ACTIONS_ENDPOINT + "/$id"

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = endpoint,
                method = NetworkMethod.GET
            ),
            responseSerializer = ActionResponse.serializer(),
        )
    }

    override suspend fun createAction(action: ActionModel): NetworkResponse<ActionResponse> {
        val body = action.toData()
        val jsonBody = JsonHelper.toJson(body, ActionDTO.serializer())

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = ActionEndpoints.ACTIONS_ENDPOINT,
                method = NetworkMethod.POST,
                jsonBody = jsonBody
            ),
            responseSerializer = ActionResponse.serializer(),
        )
    }

    override suspend fun updateAction(action: ActionModel): NetworkResponse<ActionResponse> {
        val endpoint = ActionEndpoints.ACTIONS_ENDPOINT + "/${action.id}"

        val body = action.toData()
        val jsonBody = JsonHelper.toJson(body, ActionDTO.serializer())

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = endpoint,
                method = NetworkMethod.PUT,
                jsonBody = jsonBody
            ),
            responseSerializer = ActionResponse.serializer(),
        )
    }

    override suspend fun deleteActionById(id: Long): NetworkResponse<Unit> {
        val endpoint = ActionEndpoints.ACTIONS_ENDPOINT + "/$id"

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = endpoint,
                method = NetworkMethod.DELETE,
            )
        )
    }
}