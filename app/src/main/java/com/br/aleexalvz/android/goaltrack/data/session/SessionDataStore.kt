package com.br.aleexalvz.android.goaltrack.data.session

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.br.aleexalvz.android.goaltrack.data.helper.CryptoManager
import com.br.aleexalvz.android.goaltrack.domain.model.Session
import com.br.aleexalvz.goaltrack.datastore.SessionProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionDataStore(
    context: Context,
    private val crypto: CryptoManager
) {

    private val dataStore = DataStoreFactory.create(
        serializer = SessionProtoSerializer,
        produceFile = { context.dataStoreFile("session.pb") }
    )

    val sessionFlow: Flow<Session?> =
        dataStore.data.map { proto ->
            if (proto.encryptedAuthToken.isBlank()) return@map null

            Session(
                authToken = crypto.decrypt(proto.encryptedAuthToken),
                email = crypto.decrypt(proto.encryptedEmail),
                fullName = crypto.decrypt(proto.encryptedFullname)
            )
        }

    suspend fun save(session: Session) {
        dataStore.updateData {
            it.toBuilder()
                .setEncryptedAuthToken(crypto.encrypt(session.authToken))
                .setEncryptedEmail(crypto.encrypt(session.email))
                .setEncryptedFullname(crypto.encrypt(session.fullName))
                .build()
        }
    }

    suspend fun clear() {
        dataStore.updateData { SessionProto.getDefaultInstance() }
    }
}