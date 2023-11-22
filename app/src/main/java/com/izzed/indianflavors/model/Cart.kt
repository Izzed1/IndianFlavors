package com.izzed.indianflavors.model

data class Cart(
    var id: Int? = null,
    var productId: Int? = null,
    val productName: String,
    val productPrice: Int,
    val productImgUrl: String,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null
)
