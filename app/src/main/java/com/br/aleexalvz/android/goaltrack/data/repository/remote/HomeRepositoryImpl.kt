package com.br.aleexalvz.android.goaltrack.data.repository.remote

import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.getOrNull
import com.br.aleexalvz.android.goaltrack.data.model.HomeEndpoints
import com.br.aleexalvz.android.goaltrack.data.model.response.HomeResponse
import com.br.aleexalvz.android.goaltrack.domain.model.home.GoalStatusCardModel
import com.br.aleexalvz.android.goaltrack.domain.model.home.HomeModel
import com.br.aleexalvz.android.goaltrack.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val networkProvider: NetworkProvider
) : HomeRepository {
    override suspend fun getHome(): Result<HomeModel> {

        return networkProvider.request(
            NetworkRequest(
                method = NetworkMethod.GET,
                endpoint = HomeEndpoints.HOME_ENDPOINT
            ),
            responseSerializer = HomeResponse.serializer()
        ).getOrNull()?.let { response ->
            Result.success(
                HomeModel(
                    GoalStatusCardModel(response.goalStatusCard.goalsInProgress)
                )
            )
        } ?: Result.failure(Exception())

    }
}