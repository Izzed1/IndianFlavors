package com.izzed.indianflavors.data.repository

import com.izzed.indianflavors.data.local.database.datasource.CartDataSource
import com.izzed.indianflavors.data.local.database.entity.CartEntity
import com.izzed.indianflavors.data.local.database.mapper.toCartEntity
import com.izzed.indianflavors.data.local.database.mapper.toCartList
import com.izzed.indianflavors.data.network.api.datasource.RestaurantDataSource
import com.izzed.indianflavors.data.network.api.model.order.OrderItemRequest
import com.izzed.indianflavors.data.network.api.model.order.OrderRequest
import com.izzed.indianflavors.model.Cart
import com.izzed.indianflavors.model.Menu
import com.izzed.indianflavors.utils.ResultWrapper
import com.izzed.indianflavors.utils.proceed
import com.izzed.indianflavors.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.Exception
import java.lang.IllegalStateException

interface CartRepository {
    fun getUserCardData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>>
    suspend fun createCart(menu: Menu, totalQuantity: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun order(item: List<Cart>): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAll()
}

class CartRepositoryImpl(
    private val dataSource: CartDataSource,
    private val restaurantDataSource: RestaurantDataSource
) : CartRepository {

    override fun getUserCardData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>> {
        return dataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf {
                        val pricePerItem = it.productPrice
                        val quantity = it.itemQuantity
                        pricePerItem * quantity
                    }
                    Pair(result, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == true) {
                    ResultWrapper.Empty(it.payload)
                } else {
                    it
                }
            }
            .catch {
                emit(ResultWrapper.Error(Exception(it)))
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override suspend fun createCart(
        menu: Menu,
        totalQuantity: Int
    ): Flow<ResultWrapper<Boolean>> {
        return menu.name?.let { productName ->
            proceedFlow {
                val affectedRow = dataSource.insertCart(
                    CartEntity(
                        productName = productName,
                        itemQuantity = totalQuantity,
                        productImgUrl = menu.imgUrl,
                        productPrice = menu.price
                    )
                )
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Product Name not found")))
        }
    }

    override suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { dataSource.deleteCart(modifiedCart.toCartEntity()) > 0 }
        } else {
            proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override suspend fun order(items: List<Cart>): Flow<ResultWrapper<Boolean>> {
        val orderItems = items.map {
            OrderItemRequest(it.productName, it.itemQuantity, it.itemNotes, it.productPrice.toDouble())
        }

        val total = items.sumByDouble { it.itemQuantity * it.productPrice.toDouble() }
        val orderRequest = OrderRequest(total.toInt(), orderItems)

        return proceedFlow {
            val response = restaurantDataSource.createOrder(orderRequest)
            response.status == true
        }
    }

    override suspend fun deleteAll() {
        dataSource.deleteAll()
    }
}
