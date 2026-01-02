package com.br.aleexalvz.android.goaltrack.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleexalvz.login.presentation.model.LoginUIState
import com.br.aleexalvz.android.goaltrack.login.presentation.model.LoginEvent
import com.br.aleexalvz.android.goaltrack.login.presentation.model.LoginUIAction
import com.br.aleexalvz.android.goaltrack.model.LoginBody
import com.br.aleexalvz.android.goaltrack.network.data.NetworkRequest
import com.br.aleexalvz.android.goaltrack.network.data.NetworkRequestMethod
import com.br.aleexalvz.android.goaltrack.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.network.util.JsonUtils
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

    private val _loginState = MutableStateFlow(LoginUIState())
    val loginState: StateFlow<LoginUIState> = _loginState.asStateFlow()

    private val _loginEvents = MutableSharedFlow<LoginEvent>()
    val loginEvents = _loginEvents.asSharedFlow()

    fun onUIAction(uiAction: LoginUIAction) = viewModelScope.launch(IO) {
        when (uiAction) {
            is LoginUIAction.UpdateEmail -> updateEmail(uiAction.email)

            is LoginUIAction.UpdatePassword -> updatePassword(uiAction.password)

            is LoginUIAction.UpdateRememberMeCheckBox -> updateRememberMe(uiAction.rememberMe)

            is LoginUIAction.Login -> performLogin(uiAction.email, uiAction.password)
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
        val body = LoginBody(email, password)
        val jsonBody = JsonUtils.toJson(body, LoginBody.serializer())
        networkProvider.request(
            networkRequest = NetworkRequest(
                endpoint = "auth/login",
                method = NetworkRequestMethod.POST,
                bodyJson = jsonBody
            ),
            responseType = String::class.java
        )
    }
}
