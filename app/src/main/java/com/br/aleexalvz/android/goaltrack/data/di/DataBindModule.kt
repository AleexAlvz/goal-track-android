package com.br.aleexalvz.android.goaltrack.data.di

import com.br.aleexalvz.android.goaltrack.data.repository.AuthRepositoryImpl
import com.br.aleexalvz.android.goaltrack.data.repository.SessionRepositoryImpl
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import com.br.aleexalvz.android.goaltrack.domain.usecase.LoginUseCase
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
}