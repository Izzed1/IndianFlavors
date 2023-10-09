package com.izzed.indianflavors.data.repository

import com.izzed.indianflavors.data.dummy.DummyCategoryDataSource
import com.izzed.indianflavors.data.local.database.datasource.ProductDataSource
import com.izzed.indianflavors.data.local.database.mapper.toProductList
import com.izzed.indianflavors.model.Category
import com.izzed.indianflavors.model.Product
import com.izzed.indianflavors.utils.ResultWrapper
import com.izzed.indianflavors.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface ProductRepository {
    fun getCategories(): List<Category>
    fun getProducts(): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val categoryDataSource: DummyCategoryDataSource,
) : ProductRepository {

    override fun getCategories(): List<Category> {
        return categoryDataSource.getCategories()
    }

    override fun getProducts(): Flow<ResultWrapper<List<Product>>> {
        return productDataSource.getAllProducts().map { proceed { it.toProductList() } }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}