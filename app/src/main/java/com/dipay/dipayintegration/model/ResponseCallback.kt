package com.example.dipayintegration.model

import com.google.gson.annotations.SerializedName

data class ResponseCallback<T>(
    @SerializedName("code")
    val code: String,

    @SerializedName("data")
    val data: T
)