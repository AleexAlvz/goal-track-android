package com.br.aleexalvz.android.goaltrack.core.network.di

import com.br.aleexalvz.android.goaltrack.core.network.data.AuthInterceptor
import com.br.aleexalvz.android.goaltrack.core.network.data.NetworkProviderImpl
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.domain.usecase.AuthAuthenticator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {

    @Binds
    abstract fun bindNetworkProvider(
        impl: NetworkProviderImpl
    ): NetworkProvider

    @Binds
    @AuthSessionInterceptor
    abstract fun provideAuthInterceptor(
        impl: AuthInterceptor
    ): Interceptor

    @Binds
    @RefreshAuthAuthenticator
    abstract fun provideAuthAuthenticator(
        impl: AuthAuthenticator
    ): Authenticator
}