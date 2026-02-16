package com.br.aleexalvz.android.goaltrack.data.model.response

import com.br.aleexalvz.android.goaltrack.domain.model.note.NoteModel
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class NoteResponse(
    val id: Long,
    val actionId: Long,
    val date: String,
    val notes: String
)

fun NoteResponse.toNoteModel() = NoteModel(
    id = id,
    actionId = actionId,
    executionDate = LocalDate.parse(date),
    notes = notes
)