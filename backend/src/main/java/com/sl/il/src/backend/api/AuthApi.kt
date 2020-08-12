package com.sl.il.src.backend.api

import com.sl.il.src.backend.model.Credentials
import com.sl.il.src.backend.model.Token
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("register")
    fun postRegister(
        @Body credentials: Credentials
    ): Observable<Token>

    @POST("login")
    fun postLogin(
        @Body credentials: Credentials
    ): Observable<Token>
}
