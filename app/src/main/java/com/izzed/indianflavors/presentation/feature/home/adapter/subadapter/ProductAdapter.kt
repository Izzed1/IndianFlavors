package com.izzed.indianflavors.presentation.feature.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.izzed.indianflavors.databinding.ItemGridProductBinding
import com.izzed.indianflavors.model.Product

class ProductAdapter(private val itemClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ItemProductViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<Product>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemProductViewHolder {
        val binding = ItemGridProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemProductViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ItemProductViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    class ItemProductViewHolder(
        private val binding: ItemGridProductBinding,
        val itemClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Product) {
            with(item) {
                binding.ivProduct.load(item.imgUrl){
                    crossfade(true)
                }
                binding.tvProductName.text = item.name
                binding.tvProductPrice.text = item.price.toString()
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}

