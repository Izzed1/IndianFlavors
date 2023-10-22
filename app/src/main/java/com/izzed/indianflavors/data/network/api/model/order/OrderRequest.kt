package com.izzed.indianflavors.data.network.api.model.order


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OrderRequest(
    @SerializedName("username")
    val username: String?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("orders")
    val orders: List<OrderItemRequest?>?,
)