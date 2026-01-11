package com.br.aleexalvz.android.goaltrack.data.repository

import com.br.aleexalvz.android.goaltrack.core.common.JsonHelper
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.auth.AuthManager
import com.br.aleexalvz.android.goaltrack.data.auth.model.LoginResponse
import com.br.aleexalvz.android.goaltrack.data.dto.LoginDTO
import com.br.aleexalvz.android.goaltrack.data.dto.RecoveryPasswordDTO
import com.br.aleexalvz.android.goaltrack.data.dto.SignupDTO
import com.br.aleexalvz.android.goaltrack.data.mapper.toData
import com.br.aleexalvz.android.goaltrack.data.model.LoginEndpoints.LOGIN_ENDPOINT
import com.br.aleexalvz.android.goaltrack.data.model.LoginEndpoints.RECOVERY_PASSWORD_ENDPOINT
import com.br.aleexalvz.android.goaltrack.data.model.LoginEndpoints.SIGNUP_ENDPOINT
import com.br.aleexalvz.android.goaltrack.domain.model.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.model.RecoveryPasswordModel
import com.br.aleexalvz.android.goaltrack.domain.model.SignupModel
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkProvider: NetworkProvider
) : AuthRepository {

    override suspend fun login(loginModel: LoginModel): NetworkResponse<Unit> {
        try {
            val body = loginModel.toData()
            val jsonBody = JsonHelper.toJson(body, LoginDTO.serializer())
            val response = networkProvider.request(
                networkRequest = NetworkRequest(
                    endpoint = LOGIN_ENDPOINT,
                    method = NetworkMethod.POST,
                    bodyJson = jsonBody
                ),
                responseSerializer = LoginResponse.serializer()
            )

            response.onSuccess { AuthManager.setAuthToken(it.token) }
            return response.toUnitResponse()
        } catch (error: Exception) {
            return NetworkResponse.Failure(error)
        }
    }

    override suspend fun signUp(signupModel: SignupModel): NetworkResponse<Unit> {
        val body = signupModel.toData()
        val jsonBody = JsonHelper.toJson(body, SignupDTO.serializer())
        val response = networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = SIGNUP_ENDPOINT,
                method = NetworkMethod.POST,
                bodyJson = jsonBody
            )
        )

        response.onSuccess { AuthManager.clearAuthenticatedUser() }
        return response
    }

    override suspend fun recoveryPassword(recoveryPasswordModel: RecoveryPasswordModel): NetworkResponse<Unit> {
        val body = recoveryPasswordModel.toData()
        val jsonBody = JsonHelper.toJson(body, RecoveryPasswordDTO.serializer())
        val response = networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = RECOVERY_PASSWORD_ENDPOINT,
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