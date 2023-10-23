package com.izzed.indianflavors.presentation.feature.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.izzed.indianflavors.R
import com.izzed.indianflavors.data.local.database.AppDatabase
import com.izzed.indianflavors.data.local.database.datasource.CartDataSource
import com.izzed.indianflavors.data.local.database.datasource.CartDatabaseDataSource
import com.izzed.indianflavors.data.network.api.datasource.RestaurantApiDataSource
import com.izzed.indianflavors.data.network.api.service.RestaurantApiService
import com.izzed.indianflavors.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.izzed.indianflavors.data.repository.CartRepository
import com.izzed.indianflavors.data.repository.CartRepositoryImpl
import com.izzed.indianflavors.data.repository.UserRepository
import com.izzed.indianflavors.data.repository.UserRepositoryImpl
import com.izzed.indianflavors.databinding.ActivityCheckoutBinding
import com.izzed.indianflavors.presentation.common.adapter.CartAdapter
import com.izzed.indianflavors.utils.GenericViewModelFactory
import com.izzed.indianflavors.utils.proceedWhen
import com.izzed.indianflavors.utils.toCurrencyFormat

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val chuckerInterceptor = ChuckerInterceptor(this.applicationContext)
        val service = RestaurantApiService.invoke(chuckerInterceptor)
        val apiDataSource = RestaurantApiDataSource(service)
        val firebaseAuth = FirebaseAuth.getInstance()
        val authDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val userRepository: UserRepository = UserRepositoryImpl(authDataSource)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource, apiDataSource, userRepository)
        GenericViewModelFactory.create(CheckoutViewModel(repo))
    }

    private val adapter: CartAdapter by lazy {
        CartAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnCheckout.setOnClickListener {
            viewModel.order()
        }
    }

    private fun observeData() {
        observeCartData()
        observeCheckoutResult()
    }

    private fun observeCheckoutResult() {
        viewModel.checkoutResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    showDialogCheckoutSuccess()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(this, "Checkout Error", Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun showDialogCheckoutSuccess() {
        AlertDialog.Builder(this)
            .setMessage("Checkout Success")
            .setPositiveButton(getString(R.string.text_okay)) { _, _ ->
                viewModel.clearCart()
                finish()
            }.create().show()
    }

    private fun observeCartData() {
        viewModel.cartList.observe(this) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.clContainerButton.isVisible = true
                result.payload?.let { (carts, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvCartPrice.text = totalPrice.toCurrencyFormat()
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.clContainerButton.isVisible = false
            }, doOnError = { err ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.clContainerButton.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                data.payload?.let { (_, totalPrice) ->
                    binding.tvCartPrice.text = totalPrice.toCurrencyFormat()
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.clContainerButton.isVisible = false
            })
        }
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
    }
}