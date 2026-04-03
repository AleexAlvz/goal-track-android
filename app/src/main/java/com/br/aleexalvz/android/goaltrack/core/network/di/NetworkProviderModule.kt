package com.br.aleexalvz.android.goaltrack.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProviderModule {

    @Provides
    @Singleton
    @MainOkhttpClient
    fun provideMainOkHttpClient(
        @RefreshAuthAuthenticator authenticator: Authenticator,
        @AuthSessionInterceptor authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .build()

    @Provides
    @Singleton
    @AuthOkhttpClient
    fun provideAuthOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
}