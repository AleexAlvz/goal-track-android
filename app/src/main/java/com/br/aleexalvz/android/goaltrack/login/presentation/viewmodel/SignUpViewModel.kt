package com.br.aleexalvz.android.goaltrack.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.aleexalvz.login.presentation.model.SignUpUIAction
import com.aleexalvz.login.presentation.model.SignUpUIState
import com.br.aleexalvz.android.goaltrack.login.presentation.model.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _signupState = MutableStateFlow(SignUpUIState())
    val signupState: StateFlow<SignUpUIState> = _signupState.asStateFlow()

    private val _signupEvent = MutableSharedFlow<LoginEvent>()
    val signupEvent = _signupEvent.asSharedFlow()

    fun onUIAction(uiAction: SignUpUIAction) {
        when (uiAction) {
            is SignUpUIAction.UpdateEmail -> _signupState.value =
                _signupState.value.copy(email = uiAction.email)

            is SignUpUIAction.UpdatePassword -> _signupState.value =
                _signupState.value.copy(password = uiAction.password)

            is SignUpUIAction.UpdateConfirmPassword -> _signupState.value =
                _signupState.value.copy(confirmPassword = uiAction.confirmPassword)
        }
    }
}