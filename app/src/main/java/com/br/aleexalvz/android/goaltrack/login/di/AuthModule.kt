package com.br.aleexalvz.android.goaltrack.login.di

import android.content.Context
import android.content.SharedPreferences
import com.br.aleexalvz.android.goaltrack.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.network.NetworkProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    private const val SHARED_PREFERENCES_KEY_REMEMBER_ME = "REMEMBER_ME_LOGIN_KEY"

    @Provides
    fun providesAuthSharedPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences =
        appContext.getSharedPreferences(SHARED_PREFERENCES_KEY_REMEMBER_ME, Context.MODE_PRIVATE)

    @Provides
    fun providesNetworkProvider(): NetworkProvider = NetworkProviderImpl()
}