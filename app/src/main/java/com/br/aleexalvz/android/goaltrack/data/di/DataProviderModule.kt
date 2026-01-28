package com.br.aleexalvz.android.goaltrack.data.di

import android.content.Context
import com.br.aleexalvz.android.goaltrack.data.helper.CryptoManager
import com.br.aleexalvz.android.goaltrack.data.session.SessionDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataProviderModule {

    @Provides
    @Singleton
    fun provideSessionDataStore(
        @ApplicationContext context: Context,
        cryptoManager: CryptoManager
    ): SessionDataStore {
        return SessionDataStore(
            context = context,
            crypto = cryptoManager
        )
    }
}