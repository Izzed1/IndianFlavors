package com.izzed.indianflavors.presentation.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemEmptyViewHolderBinding
import com.izzed.indianflavors.presentation.feature.home.adapter.model.HomeSection

class EmptyViewHolder(private val binding: ItemEmptyViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root),
    ViewHolderBinder<HomeSection> {
    override fun bind(item: HomeSection) {
    }
}
