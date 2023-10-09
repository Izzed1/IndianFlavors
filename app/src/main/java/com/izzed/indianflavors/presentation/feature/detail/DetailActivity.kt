package com.izzed.indianflavors.presentation.feature.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.izzed.indianflavors.data.local.database.AppDatabase
import com.izzed.indianflavors.data.local.database.datasource.CartDataSource
import com.izzed.indianflavors.data.local.database.datasource.CartDatabaseDataSource
import com.izzed.indianflavors.data.repository.CartRepository
import com.izzed.indianflavors.data.repository.CartRepositoryImpl
import com.izzed.indianflavors.databinding.ActivityDetailBinding
import com.izzed.indianflavors.model.Product
import com.izzed.indianflavors.utils.GenericViewModelFactory
import com.izzed.indianflavors.utils.proceedWhen
import com.izzed.indianflavors.utils.toCurrencyFormat

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(
            DetailViewModel(intent?.extras, repo)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
        bindProduct(viewModel.product)
        observeData()
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.tvProductPrice.text = it.toString()
        }
        viewModel.productCountLiveData.observe(this) {
            binding.includeItemTotalControl.tvItem.text = it.toString()
        }
        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Add to cart success !", Toast.LENGTH_SHORT).show()
                    finish()
                }, doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                })
        }
    }

    private fun bindProduct(product: Product?) {
        product?.let { item ->
            binding.ivProduct.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvProductName.text = item.name
            binding.tvProductDesc.text = item.desc
            binding.tvProductPrice.text = item.price.toString()
        }
    }

    private fun setClickListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }
        binding.clLocation.setOnClickListener {
            navigateToGoogleMaps()
        }
        binding.includeItemTotalControl.ibMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.includeItemTotalControl.ibPlus.setOnClickListener {
            viewModel.pluss()
        }
        binding.llBtnAddToCart.setOnClickListener {
            viewModel.addToCart()
        }
    }

    private fun navigateToGoogleMaps() {
        val uri = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        fun startActivity(context: Context, product: Product) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            context.startActivity(intent)
        }
    }
}