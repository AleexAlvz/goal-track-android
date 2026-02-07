package com.br.aleexalvz.android.goaltrack.data.session

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.br.aleexalvz.android.goaltrack.data.helper.CryptoManager
import com.br.aleexalvz.android.goaltrack.domain.model.login.Session
import com.br.aleexalvz.goaltrack.datastore.SessionProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Should be used only in app initialization. Recoveries from DataStore the last session persisted.
 */
class SessionDataStore(
    context: Context,
    private val crypto: CryptoManager
) {
    private val dataStore = DataStoreFactory.create(
        serializer = SessionProtoSerializer,
        produceFile = { context.dataStoreFile("session.pb") }
    )

    /**
     * Used only to return last persisted session.
     * Alert: AuthToken is not saved and should be requested with refresh session if valid.
     */
    val sessionFlow: Flow<Session?> =
        dataStore.data.map { proto ->
            if (proto.encryptedRefreshToken.isBlank()) return@map null

            Session(
                refreshToken = crypto.decrypt(proto.encryptedRefreshToken),
                email = crypto.decrypt(proto.encryptedEmail),
                fullName = crypto.decrypt(proto.encryptedFullname)
            )
        }

    suspend fun save(session: Session) {
        dataStore.updateData {
            it.toBuilder()
                .setEncryptedRefreshToken(crypto.encrypt(session.refreshToken))
                .setEncryptedEmail(crypto.encrypt(session.email))
                .setEncryptedFullname(crypto.encrypt(session.fullName))
                .build()
        }
    }

    suspend fun clear() {
        dataStore.updateData { SessionProto.getDefaultInstance() }
    }
}