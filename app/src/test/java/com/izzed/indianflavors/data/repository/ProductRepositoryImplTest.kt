package com.izzed.indianflavors.data.repository

import app.cash.turbine.test
import com.izzed.indianflavors.data.network.api.datasource.RestaurantDataSource
import com.izzed.indianflavors.data.network.api.model.category.CategoryItemResponse
import com.izzed.indianflavors.data.network.api.model.category.CategoryResponse
import com.izzed.indianflavors.data.network.api.model.menu.MenuItemResponse
import com.izzed.indianflavors.data.network.api.model.menu.MenusResponse
import com.izzed.indianflavors.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    @MockK
    lateinit var remoteDataSource: RestaurantDataSource

    private lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ProductRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `get categories, with result loading`() {
        val mockCategoryResponse = mockk<CategoryResponse>()
        runTest {
            coEvery { remoteDataSource.getCategory() } returns mockCategoryResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getCategory() }
            }
        }
    }

    @Test
    fun `get categories, with result success`() {
        val fakeCategoryResponse = CategoryItemResponse(
            imageUrl = "url",
            name = "name"
        )
        val fakeCategoriesRespose = CategoryResponse(
            code = 200,
            status = true,
            message = "success",
            data = listOf(fakeCategoryResponse)
        )
        runTest {
            coEvery { remoteDataSource.getCategory() } returns fakeCategoriesRespose
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.name, "name")
                coVerify { remoteDataSource.getCategory() }
            }
        }
    }

    @Test
    fun `get categories, with result empty`() {
        val fakeCategoriesRespose = CategoryResponse(
            code = 200,
            status = true,
            message = "success",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getCategory() } returns fakeCategoriesRespose
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getCategory() }
            }
        }
    }

    @Test
    fun `get categories, with result error`() {
        runTest {
            coEvery { remoteDataSource.getCategory() } throws IllegalAccessException("Mock Error")
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getCategory() }
            }
        }
    }

    @Test
    fun `get menu, with result loading`() {
        val fakeMenuResponse = mockk<MenusResponse>()
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } returns fakeMenuResponse
            repository.getMenus("food").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menu, with result success`() {
        val fakeMenuResponse = MenuItemResponse(
            address = "Semarang",
            desc = "description",
            price = 15000,
            formattedPrice = "15000",
            imageUrl = "url",
            name = "name"
        )
        val fakeMenusRespose = MenusResponse(
            code = 200,
            status = true,
            message = "success",
            data = listOf(fakeMenuResponse)
        )
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } returns fakeMenusRespose
            repository.getMenus("food").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.name, "name")
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menus, with result empty`() {
        val fakeMenusRespose = MenusResponse(
            code = 200,
            status = true,
            message = "success",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getMenus(any()) } returns fakeMenusRespose
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getMenus() }
            }
        }
    }

    @Test
    fun `get menus, with result error`() {
        runTest {
            coEvery { remoteDataSource.getMenus() } throws IllegalAccessException("Mock Error")
            repository.getMenus("food").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getMenus(any()) }
            }
        }
    }
}
