package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.domain.model.home.HomeModel

interface HomeRepository {
    suspend fun getHome(): Result<HomeModel>
}