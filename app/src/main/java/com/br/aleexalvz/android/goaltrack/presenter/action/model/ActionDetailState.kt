package com.br.aleexalvz.android.goaltrack.presenter.action.model

import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.model.note.NoteModel

data class ActionDetailState(
    val action: ActionModel? = null,
    val notes: List<NoteModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorToFetch: Boolean = false
)