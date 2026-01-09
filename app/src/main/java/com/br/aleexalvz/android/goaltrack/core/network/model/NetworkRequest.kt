package com.br.aleexalvz.android.goaltrack.core.network.model

data class NetworkRequest(
    val endpoint: String,
    val method: NetworkMethod = NetworkMethod.GET,
    val bodyJson: String? = null,
    val headers: HashMap<String, String>? = null
)
