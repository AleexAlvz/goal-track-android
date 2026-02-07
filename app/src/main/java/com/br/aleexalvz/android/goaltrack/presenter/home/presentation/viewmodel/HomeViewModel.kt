package com.br.aleexalvz.android.goaltrack.presenter.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.domain.model.home.GoalStatusCardModel
import com.br.aleexalvz.android.goaltrack.domain.repository.HomeRepository
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.model.GoalStatusCardState
import com.br.aleexalvz.android.goaltrack.presenter.home.presentation.model.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun getHome() = viewModelScope.launch(IO) {

        homeRepository.getHome().getOrNull()?.let { home ->
            _state.update {
                it.copy(
                    goalStatusCardState = home.goalStatusCardModel.getGoalStatusCardState()
                )
            }
        }
    }


    private fun GoalStatusCardModel.getGoalStatusCardState(): GoalStatusCardState =
        if (goalsInProgress >= 1) {
            GoalStatusCardState.InProgress(goals = goalsInProgress)
        } else {
            GoalStatusCardState.NotInProgress
        }
}