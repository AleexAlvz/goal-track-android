package com.br.aleexalvz.android.goaltrack.core.network.data

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkHeaders
import com.br.aleexalvz.android.goaltrack.data.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SessionManager.session?.authToken

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader(NetworkHeaders.AUTHORIZATION, "Bearer $it")
            }
        }.build()
        return chain.proceed(request)
    }
}