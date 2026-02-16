package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.NoteResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.NotesResponse
import com.br.aleexalvz.android.goaltrack.domain.model.note.NoteModel
import java.time.YearMonth

interface NoteRepository {
    suspend fun createNote(note: NoteModel): NetworkResponse<NoteResponse>
    suspend fun deleteNoteById(id: Long): NetworkResponse<Unit>
    suspend fun getNotesByMonth(month: YearMonth): NetworkResponse<NotesResponse>
    suspend fun getNotesByAction(actionId: Long): NetworkResponse<NotesResponse>
}