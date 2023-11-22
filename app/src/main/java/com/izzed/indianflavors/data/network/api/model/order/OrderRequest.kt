package com.izzed.indianflavors.data.network.api.model.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderRequest(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("orders")
    val orders: List<OrderItemRequest?>?
)
