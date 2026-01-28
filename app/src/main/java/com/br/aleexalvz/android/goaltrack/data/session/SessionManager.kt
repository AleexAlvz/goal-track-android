package com.br.aleexalvz.android.goaltrack.data.session

import com.br.aleexalvz.android.goaltrack.domain.model.Session

object SessionManager {
    private var session: Session? = null

    fun getAuthToken(): String? = session?.authToken

    fun isSessionValid(): Boolean = !session?.authToken.isNullOrBlank()

    fun saveSession(newSession: Session) { session = newSession }

    fun clearSession() { session = null }

}