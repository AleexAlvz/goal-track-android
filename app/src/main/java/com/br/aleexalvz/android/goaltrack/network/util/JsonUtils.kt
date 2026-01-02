package com.br.aleexalvz.android.goaltrack.network.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

object JsonUtils {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun <T> fromJson(jsonString: String, serializer: KSerializer<T>): T {
        return json.decodeFromString(serializer, jsonString)
    }

    fun <T> toJson(data: T, serializer: KSerializer<T>): String {
        return json.encodeToString(serializer, data)
    }
}