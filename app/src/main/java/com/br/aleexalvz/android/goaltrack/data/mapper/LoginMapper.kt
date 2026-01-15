package com.br.aleexalvz.android.goaltrack.data.mapper

import com.br.aleexalvz.android.goaltrack.data.model.dto.LoginDTO
import com.br.aleexalvz.android.goaltrack.data.model.dto.RecoveryPasswordDTO
import com.br.aleexalvz.android.goaltrack.data.model.dto.SignupDTO
import com.br.aleexalvz.android.goaltrack.domain.model.LoginModel
import com.br.aleexalvz.android.goaltrack.domain.model.RecoveryPasswordModel
import com.br.aleexalvz.android.goaltrack.domain.model.SignupModel

// Login
fun LoginModel.toData() = LoginDTO(email, password)
fun LoginDTO.toDomain() = LoginModel(email, password)

// Signup
fun SignupModel.toData() =
    SignupDTO(email = email, password = password, confirmPassword = confirmPassword)

fun SignupDTO.toDomain() =
    SignupModel(email = email, password = password, confirmPassword = confirmPassword)

// Recovery Password
fun RecoveryPasswordModel.toData() = RecoveryPasswordDTO(email = email)
fun RecoveryPasswordDTO.toDomain() = RecoveryPasswordModel(email = email)