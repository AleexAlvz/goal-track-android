package com.br.aleexalvz.android.goaltrack.data.repository.local

import com.br.aleexalvz.android.goaltrack.data.session.SessionDataStore
import com.br.aleexalvz.android.goaltrack.data.session.SessionManager
import com.br.aleexalvz.android.goaltrack.domain.model.login.Session
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : SessionRepository {

    /**
     * Should only be called in app initialization. Recoveries from DataStore the last session persisted.
     */
    override suspend fun getLastSession(): Session? =
        runCatching { sessionDataStore.sessionFlow.first() }.getOrNull()

    /**
     * Saves session in DataStore and in SessionManager. Used in Login and RefreshToken
     */
    override suspend fun saveSession(newSession: Session, persistSession: Boolean) {
        if (persistSession) {
            sessionDataStore.save(newSession)
        }
        SessionManager.saveSession(newSession)
    }

    /**
     * Clears session in DataStore and in SessionManager. Used in Logout or Expired RefreshToken
     */
    override suspend fun clearSession() {
        sessionDataStore.clear()
        SessionManager.clearSession()
    }
}