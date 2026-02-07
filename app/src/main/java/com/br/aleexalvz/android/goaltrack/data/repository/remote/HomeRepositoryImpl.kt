package com.br.aleexalvz.android.goaltrack.data.repository.remote

import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.domain.model.home.GoalStatusCardModel
import com.br.aleexalvz.android.goaltrack.domain.model.home.HomeModel
import com.br.aleexalvz.android.goaltrack.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val networkProvider: NetworkProvider
) : HomeRepository {
    override suspend fun getHome(): HomeModel {
        return HomeModel(
            GoalStatusCardModel(1)
        )
    }
}