package com.sl.il.src.backend.authenticators

import com.sl.il.src.backend.TokenStore
import com.sl.il.src.backend.api.AuthApi
import com.sl.il.src.backend.model.TokenReq
import dagger.Lazy
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

@ExperimentalStdlibApi
class TokenAuthenticator @Inject constructor(
    private val authApi: Lazy<AuthApi>,
    private val tokenStore: TokenStore
) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? =
        // This is a synchronous call
        getNewToken().let { updatedToken ->
            response.request.newBuilder()
                .header("Authorization", updatedToken)
                .build()
        }


    private fun getNewToken(): String {
        return authApi.get().postToken(
            TokenReq(
                tokenStore.getToken(
                    isEncrypted = false,
                    key = TokenStore.REFRESH_TOKEN_PREF_KEY
                )
            )
        )
            .blockingSingle().run {
                tokenStore.storeToken(token, TokenStore.TOKEN_PREF_KEY)
                token
            }
    }
}