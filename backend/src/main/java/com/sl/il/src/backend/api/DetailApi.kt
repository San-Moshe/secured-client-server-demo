package com.sl.il.src.backend.api

import com.sl.il.src.backend.model.Credentials
import io.reactivex.Observable
import retrofit2.http.GET

//TODO add JWT for requests (interceptor)
interface DetailApi {
    @GET("my-credentials")
    fun getCredentials(): Observable<Credentials>
}