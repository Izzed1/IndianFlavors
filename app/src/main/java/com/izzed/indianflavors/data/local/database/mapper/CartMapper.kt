package com.izzed.indianflavors.data.local.database.mapper

import com.izzed.indianflavors.data.local.database.entity.CartEntity
import com.izzed.indianflavors.model.Cart

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productName = this?.productName.orEmpty(),
    productImgUrl = this?.productImgUrl.orEmpty()
)

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productName = this?.productName.orEmpty(),
    productImgUrl = this?.productImgUrl.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }