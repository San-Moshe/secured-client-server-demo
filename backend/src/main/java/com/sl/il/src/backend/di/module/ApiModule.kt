package com.sl.il.src.backend.di.module

import com.sl.il.src.backend.api.AuthApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule(
    private val httpClientBuilder: OkHttpClient.Builder,
    private val gsonBuilder: GsonBuilder,
    private val env: Env = Env.PRODUCT
) {

    enum class Env(val baseUrl: String) {
        PRODUCT("http://10.0.1.23:3000/"),
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = httpClientBuilder
        .addInterceptor { chain ->
            val oldReq = chain.request()
            val newReqBuilder = oldReq.newBuilder()

            // Add headers
            newReqBuilder.addHeader("x-device-platform", "android")

            chain.proceed(newReqBuilder.build())
        }
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = gsonBuilder.create()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(env.baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
}
