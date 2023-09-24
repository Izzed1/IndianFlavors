package com.izzed.indianflavors.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemGridProductBinding
import com.izzed.indianflavors.databinding.ItemListProductBinding
import com.izzed.indianflavors.model.Product

class LinearPlanetItemViewHolder(
    private val binding : ItemListProductBinding,
    private val onClickListener : (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.ivProduct.load(item.imgUrl){
            crossfade(true)
        }
        binding.tvProductName.text = item.name
        binding.tvProductPrice.text = item.price
        binding.root.setOnClickListener{
            onClickListener.invoke(item)
        }
    }
}

class GridPlanetItemViewHolder(
    private val binding : ItemGridProductBinding,
    private val onClickListener : (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Product>{
    override fun bind(item: Product) {
        binding.ivProduct.load(item.imgUrl){
            crossfade(true)
        }
        binding.tvProductName.text = item.name
        binding.tvProductPrice.text = item.price
        binding.root.setOnClickListener{
            onClickListener.invoke(item)
        }
    }
}