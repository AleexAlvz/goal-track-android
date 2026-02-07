package com.br.aleexalvz.android.goaltrack.data.di

import com.br.aleexalvz.android.goaltrack.data.repository.remote.AuthRepositoryImpl
import com.br.aleexalvz.android.goaltrack.data.repository.remote.HomeRepositoryImpl
import com.br.aleexalvz.android.goaltrack.data.repository.local.SessionRepositoryImpl
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.HomeRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository

    @Binds
    abstract fun bindHomeRepository(
        impl: HomeRepositoryImpl
    ): HomeRepository
}