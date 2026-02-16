package com.br.aleexalvz.android.goaltrack.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NotesResponse(
    val notes: List<NoteResponse>
)