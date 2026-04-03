package com.br.aleexalvz.android.goaltrack.domain.usecase

import com.br.aleexalvz.android.goaltrack.core.common.JsonHelper
import com.br.aleexalvz.android.goaltrack.core.network.data.BASE_URL_TEST
import com.br.aleexalvz.android.goaltrack.core.network.data.JSON_MEDIA_TYPE
import com.br.aleexalvz.android.goaltrack.core.network.di.AuthOkhttpClient
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkHeaders
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.data.model.LoginEndpoints.REFRESH_ENDPOINT
import com.br.aleexalvz.android.goaltrack.data.model.dto.RefreshSessionDTO
import com.br.aleexalvz.android.goaltrack.data.model.response.LoginResponse
import com.br.aleexalvz.android.goaltrack.data.session.SessionManager
import com.br.aleexalvz.android.goaltrack.domain.model.login.Session
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    @AuthOkhttpClient private val authOkhttpClient: OkHttpClient,
    private val sessionRepository: SessionRepository
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {
        mutex.withLock<Request?> {
            val currentAuthToken = SessionManager.session?.authToken
            if (currentAuthToken?.isNotEmpty() == true &&
                isAuthTokenAlreadyRefreshed(response, currentAuthToken)
            ) {
                return@runBlocking createNewRequest(response, currentAuthToken)
            }

            if (responseCount(response) >= 2) return@runBlocking null

            val refreshToken = SessionManager.session?.refreshToken ?: return@runBlocking null

            refreshSession(refreshToken)?.let { refreshResponse ->
                saveNewSession(refreshResponse)
                return@let createNewRequest(response, refreshResponse.authToken)
            }

            return@runBlocking null
        }
    }

    private fun createNewRequest(response: Response, refreshedAuthToken: String) =
        response.request.newBuilder()
            .header(
                NetworkHeaders.AUTHORIZATION,
                "Bearer $refreshedAuthToken"
            )
            .build()

    private fun isAuthTokenAlreadyRefreshed(response: Response, currentAuthToken: String): Boolean {
        val requestToken = response.request.header(NetworkHeaders.AUTHORIZATION)
        return (requestToken != null && requestToken != "Bearer $currentAuthToken")
    }

    private suspend fun saveNewSession(refreshResponse: LoginResponse) {
        val shouldPersistSession = sessionRepository.getLastSession() != null
        sessionRepository.saveSession(
            Session(
                refreshToken = refreshResponse.refreshToken,
                authToken = refreshResponse.authToken,
                email = refreshResponse.email,
                fullName = refreshResponse.fullName
            ),
            persistSession = shouldPersistSession
        )
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }

    private fun refreshSession(refreshToken: String): LoginResponse? {
        val urlEndpoint = BASE_URL_TEST + REFRESH_ENDPOINT
        val body = RefreshSessionDTO(refreshToken)
        val jsonBody = JsonHelper.toJson(body, RefreshSessionDTO.serializer())

        val refreshRequest = Request.Builder()
            .url(urlEndpoint)
            .method(
                method = NetworkMethod.POST.name,
                body = jsonBody.toRequestBody(JSON_MEDIA_TYPE.toMediaType())
            )
            .build()

        val response = authOkhttpClient.newCall(refreshRequest).execute()
        return handleResponse(response)
    }

    private fun handleResponse(
        response: Response,
    ): LoginResponse? {
        return runCatching {
            val bodyResponse = response.body?.string()
            if (response.isSuccessful && bodyResponse != null) {
                JsonHelper.fromJson(bodyResponse, LoginResponse.serializer())
            } else {
                return null
            }
        }.fold(
            onSuccess = { it },
            onFailure = { null }
        )
    }
}