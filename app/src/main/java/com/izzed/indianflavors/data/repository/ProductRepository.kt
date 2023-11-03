package com.izzed.indianflavors.data.repository

import com.izzed.indianflavors.data.network.api.datasource.RestaurantDataSource
import com.izzed.indianflavors.data.network.api.model.category.toCategoryList
import com.izzed.indianflavors.data.network.api.model.menu.toProductList
import com.izzed.indianflavors.model.Category
import com.izzed.indianflavors.model.Menu
import com.izzed.indianflavors.utils.ResultWrapper
import com.izzed.indianflavors.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class ProductRepositoryImpl(
    private val apiDataSource: RestaurantDataSource
) : ProductRepository {

    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategory().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            apiDataSource.getMenus(category).data?.toProductList() ?: emptyList()
        }
    }
}
