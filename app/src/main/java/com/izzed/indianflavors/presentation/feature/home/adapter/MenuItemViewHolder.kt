package com.izzed.indianflavors.presentation.feature.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemGridProductBinding
import com.izzed.indianflavors.databinding.ItemListProductBinding
import com.izzed.indianflavors.model.Menu

class LinearMenuItemViewHolder(
    private val binding : ItemListProductBinding,
    private val onClickListener : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {

    }
}

class GridMenuItemViewHolder(
    private val binding : ItemGridProductBinding,
    private val onClickListener : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {

    }
}