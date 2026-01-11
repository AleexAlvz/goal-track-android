package com.br.aleexalvz.android.goaltrack.core.network.domain

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import kotlinx.serialization.KSerializer

interface NetworkProvider {
    suspend fun <T>request(
        networkRequest: NetworkRequest,
        responseSerializer: KSerializer<T>
    ): NetworkResponse<T>

    suspend fun request(
        networkRequest: NetworkRequest,
    ): NetworkResponse<Unit>
}