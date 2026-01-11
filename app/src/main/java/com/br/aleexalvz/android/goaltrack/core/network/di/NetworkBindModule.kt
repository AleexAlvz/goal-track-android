package com.br.aleexalvz.android.goaltrack.core.network.di

import com.br.aleexalvz.android.goaltrack.core.network.data.NetworkProviderImpl
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {

    @Binds
    abstract fun bindNetworkProvider(
        impl: NetworkProviderImpl
    ): NetworkProvider
}