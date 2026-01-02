package com.br.aleexalvz.android.goaltrack.network.domain

import com.br.aleexalvz.android.goaltrack.network.data.NetworkRequest
import com.br.aleexalvz.android.goaltrack.network.data.NetworkResponse

interface NetworkProvider {
    suspend fun <T>request(
        networkRequest: NetworkRequest,
        responseType: Class<T>
    ): NetworkResponse<T>
}