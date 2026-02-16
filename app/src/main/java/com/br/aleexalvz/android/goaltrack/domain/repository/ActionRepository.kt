package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.ActionResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.ActionsResponse
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel

interface ActionRepository {
    suspend fun getActionsByGoal(goalId: Long): NetworkResponse<ActionsResponse>
    suspend fun getActionById(id: Long): NetworkResponse<ActionResponse>
    suspend fun createAction(action: ActionModel): NetworkResponse<ActionResponse>
    suspend fun updateAction(action: ActionModel): NetworkResponse<ActionResponse>
    suspend fun deleteActionById(id: Long): NetworkResponse<Unit>
}