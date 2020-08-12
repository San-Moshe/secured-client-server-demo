package com.sl.il.src.backend.api

import com.sl.il.src.backend.model.Credentials
import io.reactivex.Observable
import retrofit2.http.GET

interface DetailApi {
    @GET("my-credentials")
    fun getCredentials(): Observable<Credentials>
}