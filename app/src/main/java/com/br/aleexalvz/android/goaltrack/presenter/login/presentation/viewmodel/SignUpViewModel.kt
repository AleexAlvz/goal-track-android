package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.aleexalvz.android.goaltrack.core.network.extension.onFailure
import com.br.aleexalvz.android.goaltrack.core.network.extension.onSuccess
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkException
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.domain.model.SignupModel
import com.br.aleexalvz.android.goaltrack.domain.repository.AuthRepository
import com.br.aleexalvz.android.goaltrack.presenter.helper.validateConfirmPassword
import com.br.aleexalvz.android.goaltrack.presenter.helper.validateEmail
import com.br.aleexalvz.android.goaltrack.presenter.helper.validatePassword
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.SignUpAction
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.SignUpEvent
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.SignupState
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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignupState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<SignUpEvent>()
    val events = _events.asSharedFlow()

    fun onUIAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.UpdateEmail ->
                _state.update { it.copy(email = action.email) }

            is SignUpAction.UpdatePassword ->
                _state.update { it.copy(password = action.password) }

            is SignUpAction.UpdateConfirmPassword ->
                _state.update { it.copy(confirmPassword = action.confirmPassword) }

            SignUpAction.Submit ->
                submitSignUp()
        }
    }

    private fun submitSignUp() = viewModelScope.launch {
        validateFields()
        if (hasValidFields()) {
            _state.update { it.copy(isLoading = true) }

            val response = withContext(IO) {
                authRepository.signUp(
                    SignupModel(
                        email = state.value.email,
                        password = state.value.password,
                        confirmPassword = state.value.confirmPassword,
                    )
                )
            }

            handleSignUpResponse(response)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun handleSignUpResponse(response: NetworkResponse<Unit>) {
        response.onSuccess {
            _events.emit(SignUpEvent.OnSignUpSuccess)
        }.onFailure { exception ->
            when (exception) {
                is NetworkException -> {
                    when (exception.error) {
                        NetworkError.InvalidCredentials -> _events.emit(SignUpEvent.InvalidCredentials)
                        NetworkError.ConnectionError, NetworkError.Timeout -> _events.emit(
                            SignUpEvent.ConnectionError
                        )

                        else -> _events.emit(SignUpEvent.UnexpectedError)
                    }
                }

                else -> _events.emit(SignUpEvent.UnexpectedError)
            }
        }
    }

    private fun validateFields(): Unit = with(state.value) {
        val emailResult = email.validateEmail()
        val passwordResult = password.validatePassword()
        val confirmPasswordResult = confirmPassword.validateConfirmPassword(password)

        _state.update { it.copy(emailError = emailResult.exceptionOrNull()?.message) }
        _state.update { it.copy(passwordError = passwordResult.exceptionOrNull()?.message) }
        _state.update { it.copy(confirmPasswordError = confirmPasswordResult.exceptionOrNull()?.message) }
    }

    private fun hasValidFields() = with(state.value) {
        emailError.isNullOrBlank() && passwordError.isNullOrBlank() && confirmPasswordError.isNullOrBlank()
    }
}
