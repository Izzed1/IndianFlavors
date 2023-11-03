package com.izzed.indianflavors.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.izzed.indianflavors.R
import com.izzed.indianflavors.core.ViewHolderBinder
import com.izzed.indianflavors.databinding.ItemCartBinding
import com.izzed.indianflavors.databinding.ItemCheckoutBinding
import com.izzed.indianflavors.model.Cart
import com.izzed.indianflavors.utils.doneEditing

class CartAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Cart>() {
                override fun areItemsTheSame(
                    oldItem: Cart,
                    newItem: Cart
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Cart,
                    newItem: Cart
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (cartListener != null) {
            CartViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                cartListener
            )
        } else {
            CartOrderViewHolder(
                ItemCheckoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }
}

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener?
) : ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivCart.load(item.productImgUrl) {
                crossfade(true)
            }
            includeItemTotalControl.tvItem.text = item.itemQuantity.toString()
            tvProductName.text = item.productName
            tvProductPrice.text = (item.itemQuantity * item.productPrice).toString()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotesItem.setText(item.itemNotes)
        binding.etNotesItem.doneEditing {
            binding.etNotesItem.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNotesItem.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding) {
            includeItemTotalControl.ibMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            includeItemTotalControl.ibPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            ibRemove.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
        }
    }
}

class CartOrderViewHolder(
    private val binding: ItemCheckoutBinding
) : ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivCheckout.load(item.productImgUrl) {
                crossfade(true)
            }
            tvTotalQuantity.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.itemQuantity.toString()
                )
            tvProductName.text = item.productName
            tvProductPrice.text = (item.itemQuantity * item.productPrice).toString()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNote.text = item.itemNotes
    }
}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}
