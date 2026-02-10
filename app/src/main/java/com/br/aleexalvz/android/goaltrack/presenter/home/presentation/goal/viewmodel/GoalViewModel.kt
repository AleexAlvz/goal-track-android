package com.br.aleexalvz.android.goaltrack.presenter.home.presentation.goal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.core.network.model.getOrNull
import com.br.aleexalvz.android.goaltrack.data.model.response.toGoalModel
import com.br.aleexalvz.android.goaltrack.domain.repository.GoalRepository
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.goal.model.GoalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _state: MutableStateFlow<GoalState> = MutableStateFlow(GoalState())
    val state = _state.asStateFlow()

    fun getGoals() {
        viewModelScope.launch(IO) {
            val goals = goalRepository.getGoals().getOrNull()?.goals?.map { it.toGoalModel() }
                ?: emptyList()
            _state.update { it.copy(goals = goals) }
        }
    }
}