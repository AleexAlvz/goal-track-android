package com.br.aleexalvz.android.goaltrack.core.network.extension

import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse

inline fun <T> NetworkResponse<T>.onSuccess(
    block: (data: T) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Success) {
        block.invoke(this.data)
    }
}

inline fun <T> NetworkResponse<T>.onFailure(
    block: (exception: Throwable) -> Unit
): NetworkResponse<T> = apply {
    if (this is NetworkResponse.Failure) {
        block.invoke(exception)
    }
}
