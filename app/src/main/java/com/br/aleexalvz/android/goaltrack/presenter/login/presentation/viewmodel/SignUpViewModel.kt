package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.aleexalvz.login.presentation.model.SignUpAction
import com.aleexalvz.login.presentation.model.SignUpState
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _signupState = MutableStateFlow(SignUpState())
    val signupState: StateFlow<SignUpState> = _signupState.asStateFlow()

    private val _signupEvent = MutableSharedFlow<LoginEvent>()
    val signupEvent = _signupEvent.asSharedFlow()

    fun onUIAction(uiAction: SignUpAction) {
        when (uiAction) {
            is SignUpAction.UpdateEmail -> _signupState.value =
                _signupState.value.copy(email = uiAction.email)

            is SignUpAction.UpdatePassword -> _signupState.value =
                _signupState.value.copy(password = uiAction.password)

            is SignUpAction.UpdateConfirmPassword -> _signupState.value =
                _signupState.value.copy(confirmPassword = uiAction.confirmPassword)
        }
    }
}