package com.izzed.indianflavors.presentation.feature.home.adapter.model

import com.izzed.indianflavors.model.Category
import com.izzed.indianflavors.model.Menu
import com.izzed.indianflavors.presentation.feature.home.adapter.HomeAdapter
import com.izzed.indianflavors.utils.ResultWrapper


sealed class HomeSection(val id : Int) {
    data object HeaderSection : HomeSection(HomeAdapter.ITEM_TYPE_HEADER)
    data object BannerSection : HomeSection(HomeAdapter.ITEM_TYPE_BANNER)
    data class CategoriesSection(val data : List<Category>) : HomeSection(HomeAdapter.ITEM_TYPE_CATEGORY_LIST)
    data class ProductsSection(val data : ResultWrapper<List<Menu>>) : HomeSection(HomeAdapter.ITEM_TYPE_PRODUCT_LIST)
}
