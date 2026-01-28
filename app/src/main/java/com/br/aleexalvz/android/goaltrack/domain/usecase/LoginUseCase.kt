package com.br.aleexalvz.android.goaltrack.domain.usecase

import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.LoginResponse
import com.br.aleexalvz.android.goaltrack.domain.model.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.model.Session
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(loginModel: LoginModel): NetworkResponse<Unit> {
        val response = authRepository.login(loginModel)
        response.onSuccess {
            sessionRepository.saveSession(
                Session(
                    authToken = it.token,
                    email = it.email,
                    fullName = it.fullName
                )
            )
        }
        return response.toUnitResponse()
    }
}

private fun NetworkResponse<LoginResponse>.toUnitResponse(): NetworkResponse<Unit> =
    when (this) {
        is NetworkResponse.Success -> NetworkResponse.Success(Unit)
        is NetworkResponse.Failure -> NetworkResponse.Failure(exception)
    }