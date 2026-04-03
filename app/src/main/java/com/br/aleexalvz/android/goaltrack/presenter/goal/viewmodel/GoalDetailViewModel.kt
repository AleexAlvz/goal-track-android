package com.br.aleexalvz.android.goaltrack.presenter.goal.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError.BadRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError.ConnectionError
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError.InvalidCredentials
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError.ServerError
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError.Timeout
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError.UnexpectedResponse
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError.Unknown
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkException
import com.br.aleexalvz.android.goaltrack.core.network.model.getOrNull
import com.br.aleexalvz.android.goaltrack.data.model.response.toActionModel
import com.br.aleexalvz.android.goaltrack.data.model.response.toGoalModel
import com.br.aleexalvz.android.goaltrack.domain.model.goal.GoalStatusEnum
import com.br.aleexalvz.android.goaltrack.domain.repository.ActionRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.GoalRepository
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailAction
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailEvent
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailState
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val actionRepository: ActionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _goalDetailState = MutableStateFlow(GoalDetailState())
    val goalDetailState = _goalDetailState.asStateFlow()

    private val _goalDetailEvent = MutableSharedFlow<GoalDetailEvent>()
    val goalDetailEvent = _goalDetailEvent.asSharedFlow()

    init {
        val goalId: Long? = savedStateHandle[HomeRoutes.GOAL_ID_ARG]
        if (goalId != null && goalId != -1L) {
            fetchGoalDetails(goalId)
            fetchGoalActions(goalId)
        }
    }

    private fun fetchGoalActions(id: Long) {
        viewModelScope.launch(IO) {
            runCatching {
                actionRepository.getActionsByGoal(id)
                    .getOrNull()?.actions?.map { it.toActionModel() } ?: emptyList()
            }.onSuccess { actions ->
                _goalDetailState.update {
                    it.copy(
                        actions = actions
                    )
                }
            }
        }
    }

    private fun fetchGoalDetails(id: Long) {
        viewModelScope.launch(IO) {
            try {
                goalRepository.getGoalById(id).onSuccess { goal ->
                    _goalDetailState.update {
                        it.copy(
                            goal = goal.toGoalModel(),
                            isLoading = false,
                            errorToFetch = false
                        )
                    }
                }.onFailure {
                    _goalDetailState.update { it.copy(isLoading = false, errorToFetch = true) }
                }
            } catch (e: Exception) {
                _goalDetailState.update { it.copy(isLoading = false, errorToFetch = true) }
            }
        }
    }

    fun onUIAction(uiAction: GoalDetailAction) {
        when (uiAction) {
            is GoalDetailAction.CompleteGoal -> completeGoal()
        }
    }

    private fun completeGoal() = viewModelScope.launch {
        _goalDetailState.value.goal?.let { actualGoal ->
            val completedGoal =
                actualGoal.copy(endDate = LocalDate.now(), status = GoalStatusEnum.COMPLETED)
            withContext(IO) {
                goalRepository.updateGoal(completedGoal)
            }.onSuccess {
                _goalDetailEvent.emit(GoalDetailEvent.Completed)
            }.onFailure {
                when (it) {
                    is NetworkException -> {
                        val event: GoalDetailEvent = when (it.error) {
                            ServerError,
                            Timeout,
                            ConnectionError -> GoalDetailEvent.ConnectionError

                            UnexpectedResponse,
                            Unknown,
                            InvalidCredentials,
                            BadRequest -> GoalDetailEvent.UnexpectedError
                        }
                        _goalDetailEvent.emit(event)
                    }

                    else -> {
                        _goalDetailEvent.emit(GoalDetailEvent.UnexpectedError)
                    }
                }
            }
        }
    }
}