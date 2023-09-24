package com.izzed.indianflavors.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val position : Int,
    val name: String,
    val price: String,
    val imgUrl: String,
    val desc: String
) : Parcelable