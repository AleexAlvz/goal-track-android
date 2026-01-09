package com.br.aleexalvz.android.goaltrack.core.network.model

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Failure(val exception: Throwable) : NetworkResponse<Nothing>()
}