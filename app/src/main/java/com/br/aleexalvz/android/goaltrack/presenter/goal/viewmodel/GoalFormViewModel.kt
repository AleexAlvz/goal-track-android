package com.br.aleexalvz.android.goaltrack.presenter.goal.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalCategoryEnum
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalModel
import com.br.aleexalvz.android.goaltrack.domain.repository.GoalRepository
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.CreateGoalAction
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.CreateGoalEvent
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.CreateGoalState
import com.br.aleexalvz.android.goaltrack.presenter.helper.validateIsNotBlank
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
class GoalFormViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val goalId: Long by lazy { savedStateHandle.get<Long>(GOAL_ID_ARG) ?: -1L }
    private val isEditMode = goalId != -1L

    private val _state = MutableStateFlow(CreateGoalState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<CreateGoalEvent>()
    val event = _event.asSharedFlow()

    init {
        if (isEditMode) {
            viewModelScope.launch(IO) {
                goalRepository.getGoalById(goalId).onSuccess {
                    _state.update {
                        it.copy(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            category = it.category
                        )
                    }
                }.onFailure {
                    _event.emit(CreateGoalEvent.UnexpectedError)
                }
            }
        }
    }

    fun onUIAction(uiAction: CreateGoalAction) {
        when (uiAction) {
            is CreateGoalAction.UpdateTitle -> {
                updateTitle(uiAction.title)
            }

            is CreateGoalAction.UpdateDescription -> {
                updateDescription(uiAction.description)
            }

            is CreateGoalAction.UpdateCategory -> {
                updateCategory(uiAction.category)
            }

            is CreateGoalAction.Submit -> {
                submitGoal()
            }
        }
    }

    private fun updateTitle(title: String) {
        _state.update { it.copy(title = title) }
    }

    private fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    private fun updateCategory(category: GoalCategoryEnum) {
        _state.update { it.copy(category = category) }
    }

    private fun submitGoal() = viewModelScope.launch {

        _state.update { it.copy(isLoading = true) }
        validateFields()

        if (hasValidFields()) {
            withContext(IO) {
                val response = if (isEditMode) {
                    goalRepository.updateGoal(goal = state.value.toGoalModel())
                } else {
                    goalRepository.createGoal(goal = state.value.toGoalModel())
                }

                response.onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(CreateGoalEvent.Created)
                }.onFailure {
                    _state.update { it.copy(isLoading = false) }
                    _event.emit(CreateGoalEvent.UnexpectedError) //TODO validar erro do repository
                }
            }
        } else {
            _state.update { it.copy(isLoading = false) }
            _event.emit(CreateGoalEvent.InvalidParams)
        }
    }

    private fun hasValidFields() = with(state.value) {
        titleError.isNullOrBlank() && descriptionError.isNullOrBlank() && categoryError.isNullOrBlank()
    }

    private fun validateFields(): Unit = with(state.value) {
        val emailResult = title.validateIsNotBlank()
        val passwordResult = description.validateIsNotBlank()
        val categoryResult = category?.name.validateIsNotBlank()

        _state.update { it.copy(titleError = emailResult.exceptionOrNull()?.message) }
        _state.update { it.copy(descriptionError = passwordResult.exceptionOrNull()?.message) }
        _state.update { it.copy(categoryError = categoryResult.exceptionOrNull()?.message) }
    }

    private fun CreateGoalState.toGoalModel() = GoalModel(
        title = title,
        description = description,
        category = category!!
    )
}