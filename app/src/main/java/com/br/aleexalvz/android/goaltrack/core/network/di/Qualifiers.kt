package com.br.aleexalvz.android.goaltrack.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainOkhttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkhttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthSessionInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshAuthAuthenticator