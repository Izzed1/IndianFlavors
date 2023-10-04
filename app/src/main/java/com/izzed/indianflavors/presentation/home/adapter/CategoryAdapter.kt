package com.izzed.indianflavors.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.izzed.indianflavors.databinding.ItemCategoryBinding
import com.izzed.indianflavors.model.Category

class CategoryAdapter (private val onItemClick: (Category) -> Unit): RecyclerView.Adapter<CategoryItemViewHolder>() {

    private val differ = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    fun setData(data: List<Category>) {
        differ.submitList(data)
        notifyItemRangeChanged(0,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            binding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            ),
            onItemClick = onItemClick
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}