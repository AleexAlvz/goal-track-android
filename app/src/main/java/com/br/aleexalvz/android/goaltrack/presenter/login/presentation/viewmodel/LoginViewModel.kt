package com.br.aleexalvz.android.goaltrack.presenter.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleexalvz.login.presentation.model.LoginState
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkMethod
import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.common.JsonHelper
import com.br.aleexalvz.android.goaltrack.domain.model.LoginModel
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginAction
import com.br.aleexalvz.android.goaltrack.presenter.login.presentation.model.LoginEvent
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
    private val networkProvider: NetworkProvider
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

            is LoginAction.Login -> performLogin(uiAction.email, uiAction.password)
        }
    }

    private suspend fun updateEmail(email: String) = _loginState.emit(
        _loginState.value.copy(email = email)
    )

    private suspend fun updatePassword(password: String) = _loginState.emit(
        _loginState.value.copy(password = password)
    )

    private suspend fun updateRememberMe(rememberMe: Boolean) = _loginState.emit(
        _loginState.value.copy(rememberMe = rememberMe)
    )

    private suspend fun performLogin(email: String, password: String) {

    }
}
