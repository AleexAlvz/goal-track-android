package com.br.aleexalvz.android.goaltrack.core.network.model

sealed interface NetworkError {
    data object InvalidCredentials : NetworkError
    data object BadRequest : NetworkError
    data object UnexpectedResponse : NetworkError
    data object ConnectionError : NetworkError
    data object Timeout : NetworkError
    data object ServerError : NetworkError
    data object Unknown : NetworkError
}