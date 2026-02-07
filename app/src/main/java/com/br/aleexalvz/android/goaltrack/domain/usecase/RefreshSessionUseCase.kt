package com.br.aleexalvz.android.goaltrack.domain.usecase

import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkException
import com.br.aleexalvz.android.goaltrack.data.model.response.toSession
import com.br.aleexalvz.android.goaltrack.data.session.SessionManager
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import javax.inject.Inject

class RefreshSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() {
        SessionManager.session?.refreshToken?.let { refreshToken ->
            val response = authRepository.refreshSession(refreshToken)

            val shouldPersistSession: Boolean = sessionRepository.getLastSession() != null

            response.onSuccess { loginResponse ->
                sessionRepository.saveSession(
                    newSession = loginResponse.toSession(),
                    persistSession = shouldPersistSession
                )
            }.onFailure {
                // TODO validar erro para só limpar sessão se refreshSession não for valido e não acontecer com problemas de internet e outros.
                when (it) {
                    is NetworkException -> {
                        handleNetworkException(it)
                    }
                }
            }
        }
    }

    private suspend fun handleNetworkException(networkException: NetworkException) {
        if (networkException.statusCode == 401) { // TODO Melhorar. 401 é unauthorized
            sessionRepository.clearSession()
            SessionManager.notifiesExpiredSession()
        }
    }
}