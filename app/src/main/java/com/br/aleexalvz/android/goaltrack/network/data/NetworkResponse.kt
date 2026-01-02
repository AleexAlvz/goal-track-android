package com.br.aleexalvz.android.goaltrack.network.data

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Failure(val code: Int, val message: String?) : NetworkResponse<Nothing>()
    data class Exception(val exception: Throwable) : NetworkResponse<Nothing>()
}