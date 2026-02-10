package com.br.aleexalvz.android.goaltrack.presenter.helper

import android.util.Patterns

class InvalidFieldException(override val message: String?) : Exception()

fun String.validateEmail(): Result<Unit> =
    when {
        isBlank() -> Result.failure(InvalidFieldException("Email obrigatório"))
        !Patterns.EMAIL_ADDRESS.matcher(this).matches() ->
            Result.failure(InvalidFieldException("Email inválido"))

        else -> Result.success(Unit)
    }

fun String.validatePassword(): Result<Unit> =
    when {
        length < 8 -> Result.failure(InvalidFieldException("Senha deve ter no mínimo 8 caracteres"))
        none { it.isUpperCase() } || none { it.isLowerCase() } || none { it.isDigit() } ->
            Result.failure(
                InvalidFieldException(
                    "Senha deve conter letra maiúscula, minúscula e número"
                )
            )

        else -> Result.success(Unit)
    }

fun String.validateConfirmPassword(password: String): Result<Unit> =
    when {
        this != password ->
            Result.failure(InvalidFieldException("As senhas não conferem"))

        else -> Result.success(Unit)
    }

fun String?.validateIsNotBlank(): Result<Unit> =
    when {
        this.isNullOrBlank() -> Result.failure(InvalidFieldException("O campo deve ser preenchido"))
        else -> Result.success(Unit)
    }

