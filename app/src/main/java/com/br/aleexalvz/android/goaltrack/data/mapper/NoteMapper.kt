package com.br.aleexalvz.android.goaltrack.data.mapper

import com.br.aleexalvz.android.goaltrack.data.model.dto.NoteDTO
import com.br.aleexalvz.android.goaltrack.domain.model.note.NoteModel

fun NoteModel.toData() = NoteDTO(
    id = id,
    actionId = actionId,
    executionDate = executionDate.toString(),
    notes = notes
)