package com.br.aleexalvz.android.goaltrack.data.mapper

import com.br.aleexalvz.android.goaltrack.data.model.dto.LoginDTO
import com.br.aleexalvz.android.goaltrack.data.model.dto.RecoveryPasswordDTO
import com.br.aleexalvz.android.goaltrack.data.model.dto.SignupDTO
import com.br.aleexalvz.android.goaltrack.domain.model.login.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.model.login.RecoveryPasswordModel
import com.br.aleexalvz.android.goaltrack.domain.model.login.SignupModel

// Login
fun LoginModel.toData() = LoginDTO(email, password)
fun LoginDTO.toDomain() = LoginModel(email, password)

// Signup
fun SignupModel.toData() =
    SignupDTO(
        email = email,
        fullName = fullName,
        password = password,
        confirmPassword = confirmPassword
    )

fun SignupDTO.toDomain() =
    SignupModel(
        email = email,
        fullName = fullName,
        password = password,
        confirmPassword = confirmPassword
    )

// Recovery Password
fun RecoveryPasswordModel.toData() = RecoveryPasswordDTO(email = email)
fun RecoveryPasswordDTO.toDomain() = RecoveryPasswordModel(email = email)