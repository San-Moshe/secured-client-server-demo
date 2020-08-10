package com.sl.il.src.backend.api

import com.sl.il.src.backend.model.Credentials
import com.sl.il.src.backend.model.MeiziPic
import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

    @POST("register")
    fun postRegister(
        @Body credentials: Credentials
    ): Observable<Credentials>

    @POST("login")
    fun postLogin(
        @Body credentials: Credentials
    ): Observable<ResponseBody>
}
