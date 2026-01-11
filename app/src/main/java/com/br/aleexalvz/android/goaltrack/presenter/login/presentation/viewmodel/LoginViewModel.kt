package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel

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
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.domain.model.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginAction
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginEvent
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _loginEvents = MutableSharedFlow<LoginEvent>()
    val loginEvents = _loginEvents.asSharedFlow()

    fun onUIAction(uiAction: LoginAction) = viewModelScope.launch(IO) {
        when (uiAction) {
            is LoginAction.UpdateEmail -> updateEmail(uiAction.email)

            is LoginAction.UpdatePassword -> updatePassword(uiAction.password)

            is LoginAction.UpdateRememberMeCheckBox -> updateRememberMe(uiAction.rememberMe)

            is LoginAction.Submit -> submitLogin()
            LoginAction.LoginWithGoogle -> {
                TODO()
            }
        }
    }

    private suspend fun updateEmail(email: String) = updateLoginState {
        it.copy(email = email)
    }

    private suspend fun updatePassword(password: String) = updateLoginState {
        it.copy(password = password)
    }

    private suspend fun updateRememberMe(rememberMe: Boolean) = updateLoginState {
        it.copy(rememberMe = rememberMe)
    }

    private suspend fun updateLoginState(block: (loginState: LoginState) -> LoginState) {
        block(_loginState.value).let { _loginState.emit(it) }
    }

    private suspend fun submitLogin() {
        updateLoginState { it.copy(isLoading = true) }

        val response = authRepository.login(
            LoginModel(
                email = loginState.value.email,
                password = loginState.value.password
            )
        )
        handleLoginResponse(response)

        updateLoginState { it.copy(isLoading = false) }
    }

    private suspend fun handleLoginResponse(response: NetworkResponse<Unit>) {
        response.onSuccess {
            _loginEvents.emit(LoginEvent.OnLoginSuccess)
        }.onFailure {
            when (it) {
                is NetworkException -> {
                    val event: LoginEvent = when (it.error) {
                        ServerError, Timeout, ConnectionError -> LoginEvent.NetworkFailure
                        InvalidCredentials, BadRequest -> LoginEvent.InvalidCredentials
                        UnexpectedResponse, Unknown -> LoginEvent.UnexpectedError
                    }
                    _loginEvents.emit(event)
                }

                else -> {
                    _loginEvents.emit(LoginEvent.UnexpectedError)
                }
            }
        }
    }
}
