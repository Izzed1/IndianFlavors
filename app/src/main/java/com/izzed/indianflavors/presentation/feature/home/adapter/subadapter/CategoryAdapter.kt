package com.izzed.indianflavors.presentation.feature.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.izzed.indianflavors.databinding.ItemCategoryBinding
import com.izzed.indianflavors.model.Category

class CategoryAdapter (private val onItemClick: (Category) -> Unit):
    RecyclerView.Adapter<CategoryAdapter.ItemCategoryViewHolder>() {

    private var items: MutableList<Category> = mutableListOf()

    fun setData(items: List<Category>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        val binding =
        ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCategoryViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class ItemCategoryViewHolder(
        private val binding: ItemCategoryBinding,
        val itemClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Category) {
            with(item) {
                binding.ivCategory.load(item.imgUrl){
                    crossfade(true)
                }
                binding.tvCategory.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}