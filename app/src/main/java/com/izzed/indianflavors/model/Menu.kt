package com.izzed.indianflavors.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val name: String? = null,
    val price: Int,
    val imgUrl: String,
    val desc: String
) : Parcelable
