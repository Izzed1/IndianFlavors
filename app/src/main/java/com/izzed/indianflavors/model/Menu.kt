package com.izzed.indianflavors.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int? = null,
    val name: String,
    val price: Double,
    val imgUrl: String,
    val desc: String
): Parcelable