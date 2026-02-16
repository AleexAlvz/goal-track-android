package com.br.aleexalvz.android.goaltrack.presenter.action.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.domain.model.action.ActionModel
import com.br.aleexalvz.android.goaltrack.domain.model.action.toActionFrequencyEnum
import com.br.aleexalvz.android.goaltrack.domain.repository.ActionRepository
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormAction
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormEvent
import com.br.aleexalvz.android.goaltrack.presenter.action.model.ActionFormState
import com.br.aleexalvz.android.goaltrack.presenter.helper.validateIsNotBlank
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes.ACTION_ID_ARG
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes.GOAL_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ActionFormViewModel @Inject constructor(
    private val actionRepository: ActionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val actionId: Long by lazy { savedStateHandle.get<Long>(ACTION_ID_ARG) ?: -1L }
    private val goalId: Long by lazy { savedStateHandle.get<Long>(GOAL_ID_ARG) ?: -1L }
    private val isEditMode = actionId != -1L

    private val _state = MutableStateFlow(ActionFormState(id = actionId, goalId = goalId))
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ActionFormEvent>()
    val event = _event.asSharedFlow()

    init {
        if (isEditMode) {
            viewModelScope.launch(IO) {
                actionRepository.getActionById(actionId).onSuccess { action ->
                    _state.update {
                        it.copy(
                            id = action.id,
                            title = action.title,
                            description = action.description,
                            frequency = action.frequency.toActionFrequencyEnum()
                        )
                    }
                }.onFailure {
                    _event.emit(ActionFormEvent.UnexpectedError)
                }
            }
        }
    }

    fun onUIAction(uiAction: ActionFormAction) {
        when (uiAction) {
            is ActionFormAction.UpdateTitle -> {
                updateTitle(uiAction.title)
            }

            is ActionFormAction.UpdateDescription -> {
                updateDescription(uiAction.description)
            }

            is ActionFormAction.UpdateFrequency -> {
                updateFrequency(uiAction.frequency)
            }

            is ActionFormAction.Submit -> {
                submitAction()
            }
        }
    }

    private fun updateFrequency(frequency: ActionFrequencyEnum) {
        _state.update { it.copy(frequency = frequency) }
    }

    private fun updateTitle(title: String) {
        _state.update { it.copy(title = title) }
    }

    private fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    private fun submitAction() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        validateFields()

        if (hasValidFields()) {
            withContext(IO) {
                val response = if (isEditMode) {
                    actionRepository.updateAction(action = state.value.toActionModel())
                } else {
                    actionRepository.createAction(action = state.value.toActionModel())
                }

                response.onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ActionFormEvent.Created)
                }.onFailure {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(ActionFormEvent.UnexpectedError)
                }
            }
        }
    }

    private fun hasValidFields() = with(state.value) {
        titleError.isNullOrBlank() && descriptionError.isNullOrBlank()
    }

    private fun validateFields(): Unit = with(state.value) {
        val titleResult = title.validateIsNotBlank()
        val descriptionResult = description.validateIsNotBlank()

        _state.update { it.copy(titleError = titleResult.exceptionOrNull()?.message) }
        _state.update { it.copy(descriptionError = descriptionResult.exceptionOrNull()?.message) }
    }

    private fun ActionFormState.toActionModel() = ActionModel(
        id = actionId,
        goalId = goalId,
        title = title,
        description = description,
        frequency = frequency
    )
}