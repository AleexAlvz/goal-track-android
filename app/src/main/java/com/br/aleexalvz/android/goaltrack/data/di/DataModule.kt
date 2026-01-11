package com.br.aleexalvz.android.goaltrack.data.di

import com.br.aleexalvz.android.goaltrack.data.repository.AuthRepositoryImpl
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}