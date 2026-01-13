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
import com.br.aleexalvz.android.goaltrack.presenter.helper.validateEmail
import com.br.aleexalvz.android.goaltrack.presenter.helper.validateIsNotBlank
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun onUIAction(uiAction: LoginAction) {
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

    private fun updateEmail(email: String) = _state.update {
        it.copy(email = email)
    }

    private fun updatePassword(password: String) = _state.update {
        it.copy(password = password)
    }

    private fun updateRememberMe(rememberMe: Boolean) = _state.update {
        it.copy(rememberMe = rememberMe)
    }

    private fun submitLogin() = viewModelScope.launch {
        validateFields()
        if (hasValidFields()) {
            _state.update { it.copy(isLoading = true) }

            val response = withContext(IO) {
                authRepository.login(
                    LoginModel(
                        email = state.value.email,
                        password = state.value.password
                    )
                )
            }
            handleLoginResponse(response)

            _state.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun handleLoginResponse(response: NetworkResponse<Unit>) {
        response.onSuccess {
            _event.emit(LoginEvent.OnLoginSuccess)
        }.onFailure {
            when (it) {
                is NetworkException -> {
                    val event: LoginEvent = when (it.error) {
                        ServerError, Timeout, ConnectionError -> LoginEvent.ConnectionError
                        InvalidCredentials, BadRequest -> LoginEvent.InvalidCredentials
                        UnexpectedResponse, Unknown -> LoginEvent.UnexpectedError
                    }
                    _event.emit(event)
                }

                else -> {
                    _event.emit(LoginEvent.UnexpectedError)
                }
            }
        }
    }

    private fun validateFields(): Unit = with(state.value) {
        val emailResult = email.validateEmail()
        val passwordResult = password.validateIsNotBlank()

        _state.update { it.copy(emailError = emailResult.exceptionOrNull()?.message) }
        _state.update { it.copy(passwordError = passwordResult.exceptionOrNull()?.message) }
    }

    private fun hasValidFields() = with(state.value) {
        emailError.isNullOrBlank() && passwordError.isNullOrBlank()
    }
}
