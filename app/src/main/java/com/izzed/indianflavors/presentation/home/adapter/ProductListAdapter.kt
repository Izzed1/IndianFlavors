package com.izzed.indianflavors.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemGridProductBinding
import com.izzed.indianflavors.databinding.ItemListProductBinding
import com.izzed.indianflavors.model.Product

class ProductListAdapter (
    var adapterLayoutMode: AdapterLayoutMode,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.position == newItem.position &&
                    oldItem.name == newItem.name &&
                    oldItem.price == newItem.price &&
                    oldItem.imgUrl == newItem.imgUrl
                    oldItem.desc == newItem.desc
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterLayoutMode.GRID.ordinal -> {
                GridPlanetItemViewHolder(
                    binding = ItemGridProductBinding.inflate(
                        LayoutInflater.from(parent.context),parent,false
                    ),onClickListener
                )
            }
            else -> {
                LinearPlanetItemViewHolder(
                    binding = ItemListProductBinding.inflate(
                        LayoutInflater.from(parent.context),parent,false
                    ),onClickListener
                )
            }
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Product>).bind(dataDiffer.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }
    fun submitData(data : List<Product>){
        dataDiffer.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0,dataDiffer.currentList.size)
    }
}