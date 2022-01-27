package com.dipay.dipayintegration.model

import com.google.gson.annotations.SerializedName


data class Activation(
    @SerializedName("secretKey")
    val secretKey: String?
)