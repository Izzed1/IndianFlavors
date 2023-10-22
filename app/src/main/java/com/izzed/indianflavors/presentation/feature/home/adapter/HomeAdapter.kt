package com.izzed.indianflavors.presentation.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemEmptyViewHolderBinding
import com.izzed.indianflavors.databinding.ItemSectionBannerHomeBinding
import com.izzed.indianflavors.databinding.ItemSectionCategoryHomeBinding
import com.izzed.indianflavors.databinding.ItemSectionHeaderHomeBinding
import com.izzed.indianflavors.databinding.ItemSectionProductHomeBinding
import com.izzed.indianflavors.model.Menu
import com.izzed.indianflavors.presentation.feature.home.adapter.model.HomeSection
import com.izzed.indianflavors.presentation.feature.home.adapter.viewholder.BannerSectionViewHolder
import com.izzed.indianflavors.presentation.feature.home.adapter.viewholder.CategoriesSectionViewHolder
import com.izzed.indianflavors.presentation.feature.home.adapter.viewholder.EmptyViewHolder
import com.izzed.indianflavors.presentation.feature.home.adapter.viewholder.HeaderSectionViewHolder
import com.izzed.indianflavors.presentation.feature.home.adapter.viewholder.ProductsSectionViewHolder

class HomeAdapter(
    private val onProductClicked: (Menu) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<HomeSection>() {
            override fun areItemsTheSame(
                oldItem: HomeSection,
                newItem: HomeSection
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: HomeSection,
                newItem: HomeSection
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<HomeSection>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> {
                HeaderSectionViewHolder(
                    ItemSectionHeaderHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM_TYPE_BANNER -> {
                BannerSectionViewHolder(
                    ItemSectionBannerHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM_TYPE_CATEGORY_LIST -> {
                CategoriesSectionViewHolder(
                    ItemSectionCategoryHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM_TYPE_PRODUCT_LIST -> {
                ProductsSectionViewHolder(
                    ItemSectionProductHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onProductClicked
                )
            }

            else -> EmptyViewHolder(
                ItemEmptyViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<HomeSection>).bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (dataDiffer.currentList[position]) {
            HomeSection.HeaderSection -> ITEM_TYPE_HEADER
            HomeSection.BannerSection -> ITEM_TYPE_BANNER
            is HomeSection.CategoriesSection -> ITEM_TYPE_CATEGORY_LIST
            is HomeSection.ProductsSection -> ITEM_TYPE_PRODUCT_LIST
        }
    }

    companion object {
        const val ITEM_TYPE_HEADER = 1
        const val ITEM_TYPE_BANNER = 2
        const val ITEM_TYPE_CATEGORY_LIST = 3
        const val ITEM_TYPE_PRODUCT_LIST = 4
    }

}