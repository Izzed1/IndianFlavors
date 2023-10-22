package com.izzed.indianflavors.data.network.api.model.order


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OrderItemRequest(
    @SerializedName("nama")
    val name: String?,
    @SerializedName("qty")
    val qty: Int?,
    @SerializedName("catatan")
    val notes: String?,
    @SerializedName("harga")
    val price: Double,
)