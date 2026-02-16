package com.br.aleexalvz.android.goaltrack.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoteDTO(
    val id: Long,
    val actionId: Long,
    val executionDate: String,
    val notes: String
)