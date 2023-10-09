package com.izzed.indianflavors.presentation.feature.home.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemSectionProductHomeBinding
import com.izzed.indianflavors.model.Product
import com.izzed.indianflavors.presentation.feature.home.adapter.model.HomeSection
import com.izzed.indianflavors.presentation.feature.home.adapter.subadapter.ProductAdapter
import com.izzed.indianflavors.utils.GridSpacingItemDecoration
import com.izzed.indianflavors.utils.proceedWhen

class ProductsSectionViewHolder(
    private val binding: ItemSectionProductHomeBinding,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    private val itemDecoration = GridSpacingItemDecoration(2, 48, true)
    init {
        binding.rvProductList.addItemDecoration(itemDecoration)
    }
    private val adapter: ProductAdapter by lazy {
        ProductAdapter {
            onClickListener.invoke(it)
        }
    }

    override fun bind(item: HomeSection) {
        if (item is HomeSection.ProductsSection) {
            item.data.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.rvProductList.apply {
                    isVisible = true
                    adapter = this@ProductsSectionViewHolder.adapter
                }
                item.data.payload?.let { data -> adapter.submitData(data) }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvProductList.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = item.data.exception?.message.orEmpty()
                binding.rvProductList.isVisible = false
            })
        }
    }
}