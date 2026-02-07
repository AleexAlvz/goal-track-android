package com.br.aleexalvz.android.goaltrack.data.session

import com.br.aleexalvz.android.goaltrack.domain.model.login.Session
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SessionManager {
    var session: Session? = null
        private set

    private val _expiredSessionEvent = MutableSharedFlow<Boolean>()
    val expiredSessionEvent = _expiredSessionEvent.asSharedFlow()

    fun saveSession(newSession: Session) {
        session = newSession
    }

    fun clearSession() {
        session = null
    }

    suspend fun notifiesExpiredSession() = _expiredSessionEvent.emit(true)
}