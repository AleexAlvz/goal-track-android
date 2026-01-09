package com.br.aleexalvz.android.goaltrack.data.auth

object AuthManager {
    private var authToken: String? = null

    fun getAuthToken(): String? = authToken

    fun isUserAuthenticated(): Boolean = !authToken.isNullOrBlank()

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun clearAuthenticatedUser() {
        authToken = null
    }
}