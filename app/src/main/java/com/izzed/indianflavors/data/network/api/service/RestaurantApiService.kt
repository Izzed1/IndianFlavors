package com.izzed.indianflavors.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.izzed.indianflavors.BuildConfig
import com.izzed.indianflavors.data.network.api.model.category.CategoryResponse
import com.izzed.indianflavors.data.network.api.model.menu.MenusResponse
import com.izzed.indianflavors.data.network.api.model.order.OrderRequest
import com.izzed.indianflavors.data.network.api.model.order.OrderResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestaurantApiService {
    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String? = null): MenusResponse

    @GET("category")
    suspend fun getCategory(): CategoryResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): RestaurantApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(RestaurantApiService::class.java)
        }
    }
}
