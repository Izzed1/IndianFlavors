package com.izzed.indianflavors.data.dummy

import com.izzed.indianflavors.model.Category

interface DummyCategoryDataSource {
    fun getCategories(): List<Category>
}

class DummyCategoryDataSourceImpl() : DummyCategoryDataSource {
    override fun getCategories(): List<Category> = listOf(
        Category(
            name = "Biryani",
            categoryImgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_biryani.png?raw=true"
        ),
        Category(
            name = "Meat",
            categoryImgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_meat.png?raw=true"
        ),
        Category(
            name = "Snacks",
            categoryImgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_snaks.png?raw=true"
        ),
        Category(
            name = "Baverages",
            categoryImgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/ic_category_baverages.png?raw=true"
        )
    )
}
