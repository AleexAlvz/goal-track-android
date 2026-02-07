package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.model.response.LoginResponse
import com.br.aleexalvz.android.goaltrack.domain.model.login.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.model.login.RecoveryPasswordModel
import com.br.aleexalvz.android.goaltrack.domain.model.login.SignupModel

interface AuthRepository {
    suspend fun login(loginModel: LoginModel): NetworkResponse<LoginResponse>
    suspend fun signUp(signupModel: SignupModel): NetworkResponse<Unit>
    suspend fun recoveryPassword(recoveryPasswordModel: RecoveryPasswordModel): NetworkResponse<Unit>
    suspend fun refreshSession(refreshToken: String): NetworkResponse<LoginResponse>
}