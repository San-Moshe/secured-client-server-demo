package com.sl.il.src.backend.interceptors

import com.sl.il.src.backend.TokenStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val tokenStore: TokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.request().run {
        if (url.encodedPath.contains("register") || url.encodedPath.contains("login")) {
            chain.proceed(this)
        } else {
            val accessToken: String = tokenStore.getToken(isEncrypted = false)
            val request: Request = newRequestWithAccessToken(this, accessToken)
            chain.proceed(request)
        }
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }
}