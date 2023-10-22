package com.izzed.indianflavors.data.network.api.datasource

import com.izzed.indianflavors.data.network.api.model.category.CategoryResponse
import com.izzed.indianflavors.data.network.api.model.menu.MenusResponse
import com.izzed.indianflavors.data.network.api.model.order.OrderRequest
import com.izzed.indianflavors.data.network.api.model.order.OrderResponse
import com.izzed.indianflavors.data.network.api.service.RestaurantApiService

interface RestaurantDataSource {
    // copy saja dari ReastaurantApiService, lalu hilangkan anotasi retrofitnya
    suspend fun getMenus(category: String? = null): MenusResponse
    suspend fun getCategory(): CategoryResponse
    suspend fun createOrder(orderRequest: OrderRequest): OrderResponse
}

class RestaurantApiDataSource(private val service: RestaurantApiService): RestaurantDataSource {
    override suspend fun getMenus(category: String?): MenusResponse {
        return service.getMenus(category)
    }


    override suspend fun getCategory (): CategoryResponse {
        return service.getCategory()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }

}