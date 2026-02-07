package com.br.aleexalvz.android.goaltrack.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.StartDestination
import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.data.model.response.LoginResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.toSession
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _startDestination = MutableStateFlow(StartDestination.LOADING.route)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    fun initializeApp() {
        viewModelScope.launch(IO) {
            validateLastSession()
        }
    }

    /**
     * 1. Get last session from DataStore
     * 2. Request to service refresh session
     * 3. If valid refreshToken, update session and emit state Logged,
     * else clear session and emit state NotLogged
     */
    private suspend fun validateLastSession() {
        sessionRepository.getLastSession()?.let { lastSession ->
            authRepository.refreshSession(lastSession.refreshToken)
                .onSuccess { loginResponse ->
                    saveRefreshedSessionAndGoToHome(loginResponse)
                }.onFailure {
                    clearSessionAndGoToLogin()
                }
        } ?: clearSessionAndGoToLogin()
    }

    private suspend fun saveRefreshedSessionAndGoToHome(loginResponse: LoginResponse) {
        sessionRepository.saveSession(
            newSession = loginResponse.toSession(),
            persistSession = true
        )
        _startDestination.value = (StartDestination.HOME.route)
    }

    private suspend fun clearSessionAndGoToLogin() {
        sessionRepository.clearSession()
        _startDestination.value = (StartDestination.LOGIN.route)
    }
}