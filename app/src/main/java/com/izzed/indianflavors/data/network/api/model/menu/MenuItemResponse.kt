package com.izzed.indianflavors.data.network.api.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.izzed.indianflavors.model.Menu

@Keep
data class MenuItemResponse(
    @SerializedName("alamat_resto")
    val address: String?,
    @SerializedName("detail")
    val desc: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun MenuItemResponse.toProduct() = Menu(
    name = this.name.orEmpty(),
    price = this.price ?: 0.0,
    imgUrl = this.imageUrl.orEmpty(),
    desc = this.desc.orEmpty()
)

fun Collection<MenuItemResponse>.toProductList() = this.map { it.toProduct() }
