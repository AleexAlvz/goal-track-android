package com.br.aleexalvz.android.goaltrack.data.repository.remote

import com.br.aleexalvz.android.goaltrack.core.common.JsonHelper
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.mapper.toData
import com.br.aleexalvz.android.goaltrack.data.model.NoteEndpoints
import com.br.aleexalvz.android.goaltrack.data.model.dto.NoteDTO
import com.br.aleexalvz.android.goaltrack.data.model.response.NoteResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.NotesResponse
import com.br.aleexalvz.android.goaltrack.domain.model.note.NoteModel
import com.br.aleexalvz.android.goaltrack.domain.repository.NoteRepository
import java.time.YearMonth
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val networkProvider: NetworkProvider
) : NoteRepository {
    override suspend fun createNote(note: NoteModel): NetworkResponse<NoteResponse> {
        val body = note.toData()
        val jsonBody = JsonHelper.toJson(body, NoteDTO.serializer())

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = NoteEndpoints.NOTES_ENDPOINT,
                method = NetworkMethod.POST,
                jsonBody = jsonBody
            ),
            responseSerializer = NoteResponse.serializer(),
        )
    }

    override suspend fun deleteNoteById(id: Long): NetworkResponse<Unit> {
        val endpoint = NoteEndpoints.NOTES_ENDPOINT + "/$id"

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = endpoint,
                method = NetworkMethod.DELETE,
            )
        )
    }

    override suspend fun getNotesByMonth(month: YearMonth): NetworkResponse<NotesResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotesByAction(actionId: Long): NetworkResponse<NotesResponse> {
        val endpoint = NoteEndpoints.NOTES_ENDPOINT + "/action/$actionId"

        return networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = endpoint,
                method = NetworkMethod.GET
            ),
            responseSerializer = NotesResponse.serializer(),
        )
    }

}