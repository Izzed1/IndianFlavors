package com.izzed.indianflavors.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.izzed.indianflavors.databinding.ItemCategoryBinding
import com.izzed.indianflavors.model.Category

class CategoryItemViewHolder(
    private val binding: ItemCategoryBinding,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Category) {

        binding.ivCategory.load(item.imgUrl)
        binding.tvCategory.text = item.name
    }
}