package com.izzed.indianflavors.data.network.api.datasource

import com.izzed.indianflavors.data.network.api.model.category.CategoryResponse
import com.izzed.indianflavors.data.network.api.model.menu.MenusResponse
import com.izzed.indianflavors.data.network.api.model.order.OrderRequest
import com.izzed.indianflavors.data.network.api.model.order.OrderResponse
import com.izzed.indianflavors.data.network.api.service.RestaurantApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RestaurantApiDataSourceTest {

    @MockK
    lateinit var service: RestaurantApiService

    private lateinit var dataSource: RestaurantDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this) // untuk mengaktifkan MockK
        dataSource = RestaurantApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val response = dataSource.getMenus("food")
            coVerify { service.getMenus(any()) } // memverifikasi apakah service sudah terpanggil
            assertEquals(response, mockResponse) // mencocokan hasil actual dengan expected
        }
    }

    @Test
    fun getCategory() {
        runTest {
            val mockResponse = mockk<CategoryResponse>(relaxed = true)
            coEvery { service.getCategory() } returns mockResponse
            val response = dataSource.getCategory()
            coVerify { service.getCategory() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrderResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
