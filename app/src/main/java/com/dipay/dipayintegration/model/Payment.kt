package com.dipay.dipayintegration.model

import com.google.gson.annotations.SerializedName


data class Payment(
    @SerializedName("payment")
    val payment: String?,

    @SerializedName("transaction")
    val transaction: String?,

    @SerializedName("amount")
    val amount: Double = 0.0
)