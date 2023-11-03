package com.izzed.indianflavors.presentation.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemGridProductBinding
import com.izzed.indianflavors.databinding.ItemListProductBinding
import com.izzed.indianflavors.model.Menu
import com.izzed.indianflavors.presentation.feature.home.adapter.model.HomeSection

class HomeAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onProductClicked: (Menu) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<HomeSection>() {
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
            }
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterLayoutMode.GRID.ordinal -> {
                GridMenuItemViewHolder(
                    binding = ItemGridProductBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onProductClicked
                )
            }
            else -> {
                LinearMenuItemViewHolder(
                    binding = ItemListProductBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onProductClicked
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<HomeSection>).bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }

    companion object {
        const val ITEM_TYPE_HEADER = 1
        const val ITEM_TYPE_BANNER = 2
        const val ITEM_TYPE_CATEGORY_LIST = 3
        const val ITEM_TYPE_PRODUCT_LIST = 4
    }
}
