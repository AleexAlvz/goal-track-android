package com.br.aleexalvz.android.goaltrack.presenter.goal.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.data.model.response.toGoalModel
import com.br.aleexalvz.android.goaltrack.domain.repository.GoalRepository
import com.br.aleexalvz.android.goaltrack.presenter.goal.data.GoalDetailState
import com.br.aleexalvz.android.goaltrack.presenter.home.navigation.HomeRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _goal = MutableStateFlow(GoalDetailState())
    val goal = _goal.asStateFlow()

    init {
        val goalId: Long? = savedStateHandle[HomeRoutes.GOAL_ID_ARG]
        if (goalId != null && goalId != -1L) {
            fetchGoalDetails(goalId)
        }
    }

    private fun fetchGoalDetails(id: Long) {
        viewModelScope.launch(IO) {
            try {
                goalRepository.getGoalById(id).onSuccess { goal ->
                    _goal.update {
                        it.copy(
                            goal = goal.toGoalModel(),
                            isLoading = false,
                            errorToFetch = false
                        )
                    }
                }.onFailure {
                    _goal.update { it.copy(isLoading = false, errorToFetch = true) }
                }
            } catch (e: Exception) {
                _goal.update { it.copy(isLoading = false, errorToFetch = true) }
            }
        }
    }
}