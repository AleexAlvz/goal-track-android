package com.br.aleexalvz.android.goaltrack.core.network.model

class NetworkException(
    val statusCode: Int,
    override val message: String?
): Exception()