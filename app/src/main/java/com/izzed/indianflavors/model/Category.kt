package com.izzed.indianflavors.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Category (
    val position: Int,
    val name: String,
    val imgUrl: String
): Parcelable