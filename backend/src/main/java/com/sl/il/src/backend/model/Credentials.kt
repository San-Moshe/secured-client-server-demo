package com.sl.il.src.backend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credentials(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
) : Parcelable
