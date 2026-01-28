package com.br.aleexalvz.android.goaltrack.data.session

import androidx.datastore.core.Serializer
import com.br.aleexalvz.goaltrack.datastore.SessionProto
import java.io.InputStream
import java.io.OutputStream

object SessionProtoSerializer : Serializer<SessionProto> {

    override val defaultValue: SessionProto = SessionProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SessionProto {
        return try {
            SessionProto.parseFrom(input)
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: SessionProto, output: OutputStream) {
        t.writeTo(output)
    }
}
