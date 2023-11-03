package com.izzed.indianflavors.presentation.feature.home.adapter.viewholder

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemSectionCategoryHomeBinding
import com.izzed.indianflavors.presentation.feature.home.adapter.model.HomeSection
import com.izzed.indianflavors.presentation.feature.home.adapter.subadapter.CategoryAdapter

class CategoriesSectionViewHolder(
    private val binding: ItemSectionCategoryHomeBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter {
            Toast.makeText(binding.root.context, it.name, Toast.LENGTH_SHORT).show()
        }
    }
    override fun bind(item: HomeSection) {
        if (item is HomeSection.CategoriesSection) {
            binding.rvCategory.apply {
                adapter = this@CategoriesSectionViewHolder.adapter
            }
        }
    }
}
