package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.domain.model.login.Session

interface SessionRepository {
    suspend fun getLastSession(): Session?
    suspend fun saveSession(newSession: Session, persistSession: Boolean)
    suspend fun clearSession()
}