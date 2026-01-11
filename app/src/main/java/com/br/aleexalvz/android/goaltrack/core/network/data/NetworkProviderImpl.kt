package com.br.aleexalvz.android.goaltrack.core.network.data

import com.br.aleexalvz.android.goaltrack.core.network.domain.NetworkProvider
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkError
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkException
import com.br.aleexalvz.android.goaltrack.core.network.model.NetworkHeaders
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
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val BASE_URL_TEST = "http://10.0.2.2:8080/"
private const val JSON_MEDIA_TYPE = "application/json; charset=utf-8"

class NetworkProviderImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : NetworkProvider {

    override suspend fun <T> request(
        networkRequest: NetworkRequest,
        responseType: Class<T>
    ): NetworkResponse<T> = try {
        val request = getRequest(networkRequest)
        val response = okHttpClient.newCall(request).execute()
        handleResponse(response, responseType)
    } catch (e: Exception) {
        NetworkResponse.Failure(e.toNetworkException())
    }

    override suspend fun request(networkRequest: NetworkRequest): NetworkResponse<Unit> {
        return request(networkRequest, Unit::class.java)
    }

    private fun <T> handleResponse(response: Response, responseType: Class<T>): NetworkResponse<T> {
        return if (response.isSuccessful) {
            handleSuccessfulResponse(response, responseType)
        } else {
            return NetworkResponse.Failure(response.toNetworkException())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> handleSuccessfulResponse(
        response: Response,
        responseType: Class<T>
    ): NetworkResponse<T> {
        val bodyResponse = response.body?.string()
        return if (bodyResponse.isNullOrBlank()) {
            if (responseType == Unit::class.java) {
                NetworkResponse.Success(bodyResponse as T) //TODO rever serialização de sucesso
            } else {
                NetworkResponse.Failure(
                    exception = NetworkException(
                        error = NetworkError.UnexpectedResponse,
                        statusCode = response.code,
                        message = response.message
                    )
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
        addAuthToken()
    }.build()

    private fun Headers.Builder.addAuthToken() {
        AuthManager.getAuthToken().takeIf { !it.isNullOrBlank() }.let {
            add(NetworkHeaders.AUTHORIZATION, "Bearer $it")
        }
    }

    private fun NetworkRequest.getUrl(): String = BASE_URL_TEST + endpoint

    private fun NetworkRequest.getRequestBody(): RequestBody? =
        bodyJson?.toRequestBody(JSON_MEDIA_TYPE.toMediaType())

    private fun Exception.toNetworkException(): NetworkException {
        val error: NetworkError = when (this) {
            is SocketTimeoutException -> NetworkError.Timeout
            is IOException -> NetworkError.ConnectionError
            else -> NetworkError.Unknown
        }
        return NetworkException(error = error, message = this.message, cause = this)
    }

    private fun Int.mapToNetworkError(): NetworkError = when (this) {
        400 -> NetworkError.BadRequest
        401, 403 -> NetworkError.InvalidCredentials
        in 500..599 -> NetworkError.ServerError
        else -> NetworkError.Unknown
    }

    private fun Response.toNetworkException(): NetworkException {
        val error = code.mapToNetworkError()
        return NetworkException(error = error, statusCode = code, message = message)
    }
}
