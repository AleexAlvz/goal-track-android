package com.br.aleexalvz.android.goaltrack.domain.model.note

import java.time.LocalDate

data class NoteModel(
    val id: Long,
    val actionId: Long,
    val executionDate: LocalDate,
    val notes: String
)