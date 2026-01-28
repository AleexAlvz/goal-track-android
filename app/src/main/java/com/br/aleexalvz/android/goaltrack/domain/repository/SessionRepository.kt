package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.domain.model.Session

interface SessionRepository {
    suspend fun validateSession(): Boolean
    suspend fun saveSession(session: Session)
    suspend fun clearSession()
}