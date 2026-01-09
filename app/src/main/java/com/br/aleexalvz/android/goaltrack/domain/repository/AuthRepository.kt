package com.br.aleexalvz.android.goaltrack.domain.repository

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.domain.model.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.model.RecoveryPasswordModel
import com.br.aleexalvz.android.goaltrack.domain.model.SignUpModel

interface AuthRepository {
    suspend fun login(loginModel: LoginModel): NetworkResponse<Unit>
    suspend fun signUp(signUpModel: SignUpModel): NetworkResponse<Unit>
    suspend fun recoveryPassword(recoveryPasswordModel: RecoveryPasswordModel): NetworkResponse<Unit>
}