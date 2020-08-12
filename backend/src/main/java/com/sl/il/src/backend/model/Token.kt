package com.sl.il.src.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token(
    @SerializedName("success") val success: String,
    @SerializedName("message") val message: String,
    @SerializedName("token") val token: String,
    @SerializedName("refreshToken") val refreshToken: String
) : Parcelable

@Parcelize
data class TokenReq(
    @SerializedName("refreshToken") val token: String
) : Parcelable
