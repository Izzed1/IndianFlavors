package com.izzed.indianflavors.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.izzed.indianflavors.databinding.FragmentDetailBinding
import com.izzed.indianflavors.model.Product
import java.text.DecimalFormat

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val product: Product? by lazy {
        DetailFragmentArgs.fromBundle(arguments as Bundle).product
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        showProductData()
        controlTotalItems()
    }

    private fun controlTotalItems() {
        var totalItems = 0

        binding.ibMinus.setOnClickListener {
            if (totalItems > 0) {
                totalItems--
                updateTotalItemsText(totalItems)
                totalPrice(totalItems)
            }
        }

        binding.ibPlus.setOnClickListener {
            totalItems++
            updateTotalItemsText(totalItems)
            totalPrice(totalItems)
        }
    }

    private fun totalPrice(totalItems: Int) {
        val price = product?.price?.replace("Rp", "")?.replace(".", "")?.trim()?.toInt()
        if (price != null) {
            val totalPrice = price * totalItems
            val formattedTotalPrice = formatPrice(totalPrice)
            binding.tvTotalPrice.text = formattedTotalPrice
        } else {
            binding.tvTotalPrice.text = "Price not available"
        }
    }

    private fun formatPrice(price: Int): String {
        val formatter = DecimalFormat("#,###")
        return "Rp " + formatter.format(price)
    }

    private fun updateTotalItemsText(totalItems: Int) {
        binding.tvItem.text = totalItems.toString()
    }

    private fun showProductData() {
        product?.let { p ->
            binding.apply {
                ivProduct.load(p.imgUrl) {
                    crossfade(true)
                }
                tvProductName.text = p.name
                tvProductPrice.text = p.price
                tvProductDesc.text = p.desc
            }
        }
    }

    private fun setClickListener() {
        binding.cvLocation.setOnClickListener{
            navigateToGoogleMaps()
        }
    }

    private fun navigateToGoogleMaps() {
        val uri = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
}