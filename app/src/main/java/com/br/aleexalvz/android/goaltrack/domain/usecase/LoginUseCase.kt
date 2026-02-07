package com.br.aleexalvz.android.goaltrack.domain.usecase

import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.LoginResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.toSession
import com.br.aleexalvz.android.goaltrack.domain.model.login.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(
        loginModel: LoginModel,
        persistSession: Boolean
    ): NetworkResponse<Unit> {
        val response = authRepository.login(loginModel)
        response.onSuccess { loginResponse ->
            sessionRepository.saveSession(
                newSession = loginResponse.toSession(),
                persistSession = persistSession
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