package com.br.aleexalvz.android.goaltrack.domain.model.note

import java.time.LocalDate

data class NoteModel(
    val id: Long = -1,
    val actionId: Long,
    val executionDate: LocalDate = LocalDate.now(),
    val notes: String
)