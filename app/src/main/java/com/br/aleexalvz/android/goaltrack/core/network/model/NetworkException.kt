package com.br.aleexalvz.android.goaltrack.core.network.model

class NetworkException(
    val error: NetworkError,
    val statusCode: Int? = null,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception()