package com.izzed.indianflavors.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.izzed.indianflavors.data.local.database.AppDatabase
import com.izzed.indianflavors.data.local.database.datasource.CartDataSource
import com.izzed.indianflavors.data.local.database.datasource.CartDatabaseDataSource
import com.izzed.indianflavors.data.local.datastore.UserPreferenceDataSource
import com.izzed.indianflavors.data.local.datastore.UserPreferenceDataSourceImpl
import com.izzed.indianflavors.data.local.datastore.appDataStore
import com.izzed.indianflavors.data.network.api.datasource.RestaurantApiDataSource
import com.izzed.indianflavors.data.network.api.datasource.RestaurantDataSource
import com.izzed.indianflavors.data.network.api.service.RestaurantApiService
import com.izzed.indianflavors.data.network.firebase.auth.FirebaseAuthDataSource
import com.izzed.indianflavors.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.izzed.indianflavors.data.repository.CartRepository
import com.izzed.indianflavors.data.repository.CartRepositoryImpl
import com.izzed.indianflavors.data.repository.ProductRepository
import com.izzed.indianflavors.data.repository.ProductRepositoryImpl
import com.izzed.indianflavors.data.repository.UserRepository
import com.izzed.indianflavors.data.repository.UserRepositoryImpl
import com.izzed.indianflavors.presentation.feature.cart.CartViewModel
import com.izzed.indianflavors.presentation.feature.checkout.CheckoutViewModel
import com.izzed.indianflavors.presentation.feature.detail.DetailViewModel
import com.izzed.indianflavors.presentation.feature.home.HomeViewModel
import com.izzed.indianflavors.presentation.feature.login.LoginViewModel
import com.izzed.indianflavors.presentation.feature.profile.ProfileViewModel
import com.izzed.indianflavors.presentation.feature.register.RegisterViewModel
import com.izzed.indianflavors.utils.PreferenceDataStoreHelper
import com.izzed.indianflavors.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { RestaurantApiService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<RestaurantDataSource> { RestaurantApiDataSource(get()) }
    }

    private val repositoryModule = module {
        single<UserRepository> { UserRepositoryImpl(get()) }
        single<CartRepository> { CartRepositoryImpl(get(), get(), get()) }
        single<ProductRepository> { ProductRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::RegisterViewModel)
        viewModel { params -> DetailViewModel(extras = params.get(), get()) }
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
