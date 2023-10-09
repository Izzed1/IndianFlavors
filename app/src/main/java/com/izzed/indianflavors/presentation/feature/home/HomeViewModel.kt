package com.izzed.indianflavors.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.izzed.indianflavors.data.repository.ProductRepository
import com.izzed.indianflavors.model.Product
import com.izzed.indianflavors.presentation.feature.home.adapter.model.HomeSection
import com.izzed.indianflavors.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class HomeViewModel(private val repo: ProductRepository) : ViewModel() {
    val homeData: LiveData<List<HomeSection>>
        get() = repo.getProducts().map {
            mapToHomeData(it)
        }.asLiveData(Dispatchers.Main)

    private fun mapToHomeData(productResult : ResultWrapper<List<Product>>): List<HomeSection> = listOf(
        HomeSection.HeaderSection,
        HomeSection.BannerSection,
        HomeSection.CategoriesSection(repo.getCategories()),
        HomeSection.ProductsSection(productResult),
    )
}