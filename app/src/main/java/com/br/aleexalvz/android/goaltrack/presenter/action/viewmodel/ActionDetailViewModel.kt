package com.br.aleexalvz.android.goaltrack.presenter.action.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.getOrNull
import com.br.aleexalvz.android.goaltrack.data.model.response.toActionModel
import com.br.aleexalvz.android.goaltrack.data.model.response.toNoteModel
import com.br.aleexalvz.android.goaltrack.domain.repository.ActionRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.NoteRepository
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionDetailState
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionDetailViewModel @Inject constructor(
    private val actionRepository: ActionRepository,
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ActionDetailState())
    val state = _state.asStateFlow()

    init {
        val actionId: Long? = savedStateHandle[HomeRoutes.ACTION_ID_ARG]
        if (actionId != null && actionId != -1L) {
            fetchActionDetail(actionId)
            fetchNotes(actionId)
        }
    }

    private fun fetchNotes(id: Long) {
        viewModelScope.launch(IO) {
            runCatching {
                noteRepository.getNotesByAction(id)
                    .getOrNull()?.notes?.map { it.toNoteModel() } ?: emptyList()
            }.onSuccess { notes ->
                _state.update {
                    it.copy(
                        notes = notes
                    )
                }
            }
        }
    }

    private fun fetchActionDetail(id: Long) {
        viewModelScope.launch(IO) {
            try {
                actionRepository.getActionById(id).onSuccess { action ->
                    _state.update {
                        it.copy(
                            action = action.toActionModel(),
                            isLoading = false,
                            errorToFetch = false
                        )
                    }
                }.onFailure {
                    _state.update { it.copy(isLoading = false, errorToFetch = true) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorToFetch = true) }
            }
        }
    }
}