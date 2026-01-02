package com.br.aleexalvz.android.goaltrack.network.data

data class NetworkRequest(
    val endpoint: String,
    val method: NetworkRequestMethod = NetworkRequestMethod.GET,
    val bodyJson: String? = null,
    val headers: HashMap<String, String>? = null
)
