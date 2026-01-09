package com.br.aleexalvz.android.goaltrack.core.network.data

import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkException
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkRequest
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkResponse
import com.br.aleexalvz.android.goaltrack.data.auth.AuthManager
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

private const val BASE_URL_TEST = "http://10.0.2.2:8080/"
private const val JSON_MEDIA_TYPE = "application/json; charset=utf-8"
private const val ERROR_RESPONSE_TYPE_BODY_TYPE_NOT_FOUND =
    "The request was a successfull but the response body was null"

class NetworkProviderImpl : NetworkProvider {
    override suspend fun <T> request(
        networkRequest: NetworkRequest,
        responseType: Class<T>
    ): NetworkResponse<T> = try {
        val client = OkHttpClient.Builder().build()
        val request = getRequest(networkRequest)
        val response = client.newCall(request).execute()
        handleResponse(response, responseType)
    } catch (error: Exception) {
        NetworkResponse.Failure(error)
    }

    override suspend fun request(networkRequest: NetworkRequest): NetworkResponse<Unit> {
        return request(networkRequest, Unit::class.java)
    }

    private fun <T> handleResponse(response: Response, responseType: Class<T>): NetworkResponse<T> {
        return if (response.isSuccessful) {
            handleSuccessfulResponse(response, responseType)
        } else {
            return NetworkResponse.Failure(
                exception = NetworkException(response.code, ERROR_RESPONSE_TYPE_BODY_TYPE_NOT_FOUND)
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> handleSuccessfulResponse(
        response: Response,
        responseType: Class<T>
    ): NetworkResponse<T> {
        val bodyResponse = response.body?.string()
        return if (bodyResponse.isNullOrBlank()) {
            if (responseType::class == Unit::class) {
                NetworkResponse.Success(Unit as T)
            } else {
                NetworkResponse.Failure(
                    exception = NetworkException(response.code, response.message)
                )
            }
        } else {
            NetworkResponse.Success(bodyResponse as T)
        }
    }

    private fun getRequest(networkRequest: NetworkRequest) =
        Request.Builder()
            .url(networkRequest.getUrl())
            .headers(networkRequest.getOkhttpHeaders())
            .method(
                method = networkRequest.method.name,
                body = networkRequest.getRequestBody()
            )
            .build()

    private fun NetworkRequest.getOkhttpHeaders(): Headers = Headers.Builder().apply {
        headers?.forEach { (key, value) -> add(key, value) }
        AuthManager.getAuthToken().takeIf { !it.isNullOrBlank() }.let {
            add("Authorization", "Bearer $it") //TODO validate
        }
    }.build()

    private fun NetworkRequest.getUrl(): String = BASE_URL_TEST + endpoint

    private fun NetworkRequest.getRequestBody(): RequestBody? =
        bodyJson?.toRequestBody(JSON_MEDIA_TYPE.toMediaType())
}