package com.br.aleexalvz.android.goaltrack.data.repository

import com.br.aleexalvz.android.goaltrack.data.session.SessionDataStore
import com.br.aleexalvz.android.goaltrack.data.session.SessionManager
import com.br.aleexalvz.android.goaltrack.domain.model.Session
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : SessionRepository {

    override suspend fun validateSession(): Boolean =
        sessionDataStore.sessionFlow.first()?.authToken?.isNotBlank() == true

    override suspend fun saveSession(session: Session) {
        sessionDataStore.save(session)
        SessionManager.saveSession(session)
    }

    override suspend fun clearSession() {
        sessionDataStore.clear()
        SessionManager.clearSession()
    }
}