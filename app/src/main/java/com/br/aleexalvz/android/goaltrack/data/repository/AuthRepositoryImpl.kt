package com.br.aleexalvz.android.goaltrack.data.repository

import com.br.aleexalvz.android.goaltrack.core.common.JsonHelper
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.auth.AuthManager
import com.br.aleexalvz.android.goaltrack.data.auth.model.LoginResponse
import com.br.aleexalvz.android.goaltrack.domain.model.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.model.RecoveryPasswordModel
import com.br.aleexalvz.android.goaltrack.domain.model.SignUpModel
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository

class AuthRepositoryImpl(private val networkProvider: NetworkProvider) : AuthRepository {

    override suspend fun login(loginModel: LoginModel): NetworkResponse<Unit> {
        val body = LoginModel(loginModel.email, loginModel.password)
        val jsonBody = JsonHelper.toJson(body, LoginModel.serializer())
        val response = networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = "auth/login",
                method = NetworkMethod.POST,
                bodyJson = jsonBody
            ),
            responseType = LoginResponse::class.java
        )

        response.onSuccess { AuthManager.setAuthToken(it.token) }
        return response.toUnitResponse()
    }

    override suspend fun signUp(signUpModel: SignUpModel): NetworkResponse<Unit> {
        val body = SignUpModel(signUpModel.email, signUpModel.password)
        val jsonBody = JsonHelper.toJson(body, SignUpModel.serializer())
        val response = networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = "auth/register",
                method = NetworkMethod.POST,
                bodyJson = jsonBody
            )
        )

        response.onSuccess { AuthManager.clearAuthenticatedUser() }
        return response
    }

    override suspend fun recoveryPassword(recoveryPasswordModel: RecoveryPasswordModel): NetworkResponse<Unit> {
        val body = RecoveryPasswordModel(recoveryPasswordModel.email)
        val jsonBody = JsonHelper.toJson(body, RecoveryPasswordModel.serializer())
        val response = networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = "auth/register",
                method = NetworkMethod.POST,
                bodyJson = jsonBody
            )
        )

        return response
    }

    private fun NetworkResponse<LoginResponse>.toUnitResponse(): NetworkResponse<Unit> =
        when (this) {
            is NetworkResponse.Success -> NetworkResponse.Success(Unit)
            is NetworkResponse.Failure -> NetworkResponse.Failure(exception)
        }
}