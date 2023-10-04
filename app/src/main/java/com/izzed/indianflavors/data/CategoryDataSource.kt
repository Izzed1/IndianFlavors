package com.izzed.indianflavors.data

import com.izzed.indianflavors.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}

class CategoryDataSourceImpl(): CategoryDataSource {
    override fun getCategories(): List<Category> = listOf(
        Category(
            position = 1,
            name = "Biryani",
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_biryani.png?raw=true"
        ),
        Category(
            position = 2,
            name = "Meat",
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_meat.png?raw=true"
        ),
        Category(
            position = 3,
            name = "Snacks",
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_snaks.png?raw=true"
        ),
        Category(
            position = 4,
            name = "Baverages",
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_baverages.png?raw=true"
        ),
    )
}