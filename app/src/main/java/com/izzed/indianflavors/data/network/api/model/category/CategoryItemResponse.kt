package com.izzed.indianflavors.data.network.api.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.izzed.indianflavors.model.Category

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun CategoryItemResponse.toCategory() = Category(
    name = this.name.orEmpty(),
    categoryImgUrl = this.imageUrl.orEmpty()
)

fun Collection<CategoryItemResponse>.toCategoryList() = this.map {
    it.toCategory()
}