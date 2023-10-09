package com.izzed.indianflavors.model

import java.util.UUID

data class Category (
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val imgUrl: String
)