package com.izzed.indianflavors.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.izzed.indianflavors.R
import com.izzed.indianflavors.data.ProductDataSource
import com.izzed.indianflavors.data.ProductDataSourceImpl
import com.izzed.indianflavors.databinding.FragmentHomeBinding
import com.izzed.indianflavors.model.Product
import com.izzed.indianflavors.presentation.home.adapter.AdapterLayoutMode
import com.izzed.indianflavors.presentation.home.adapter.ProductListAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val dataSource: ProductDataSource by lazy {
        ProductDataSourceImpl()
    }

    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(AdapterLayoutMode.LINEAR) { product: Product ->
            navigateToDetail(product)
        }
    }

    private fun navigateToDetail(product: Product) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(product)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupControl()
    }

    private fun setupList() {
        val span = if(adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvProduct.apply {
            layoutManager = GridLayoutManager(requireContext(),span)
            adapter = this@HomeFragment.adapter
        }
        adapter.submitData(dataSource.getProducts())
    }

    private fun setupControl() {
        binding.ivIbSetupControl.setOnClickListener() {
            val newLayoutMode =
                if (adapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) AdapterLayoutMode.GRID
                else AdapterLayoutMode.LINEAR
            val newSpanCount = if (newLayoutMode == AdapterLayoutMode.GRID) 2 else 1

            adapter.adapterLayoutMode = newLayoutMode
            (binding.rvProduct.layoutManager as GridLayoutManager).spanCount = newSpanCount

            val iconResource =
                if (newLayoutMode == AdapterLayoutMode.GRID) R.drawable.ic_grid
                else R.drawable.ic_list
            binding.ivIbSetupControl.setImageResource(iconResource)
        }
    }
}