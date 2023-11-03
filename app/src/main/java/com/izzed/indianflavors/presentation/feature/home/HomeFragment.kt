package com.izzed.indianflavors.presentation.feature.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.izzed.indianflavors.R
import com.izzed.indianflavors.databinding.FragmentHomeBinding
import com.izzed.indianflavors.model.Menu
import com.izzed.indianflavors.presentation.feature.detail.DetailActivity
import com.izzed.indianflavors.presentation.feature.home.adapter.AdapterLayoutMode
import com.izzed.indianflavors.presentation.feature.home.adapter.subadapter.CategoryAdapter
import com.izzed.indianflavors.presentation.feature.home.adapter.subadapter.MenuAdapter
import com.izzed.indianflavors.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            viewModel.getMenus(it.name)
        }
    }

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter {
            navigateToDetail(it)
        }
    }

    private fun navigateToDetail(item: Menu) {
        DetailActivity.startActivity(requireContext(), item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        getMenus()
        setClickListener()
    }

    private fun setClickListener() {
        var isIconChanged = false

        binding.ivIbSetupControl.setOnClickListener {
            if (isIconChanged) {
                binding.ivIbSetupControl.setImageResource(R.drawable.ic_list)
                val newLayoutMode = AdapterLayoutMode.LINEAR
                setAdapterLayoutMode(newLayoutMode)
            } else {
                binding.ivIbSetupControl.setImageResource(R.drawable.ic_grid)
                val newLayoutMode = AdapterLayoutMode.GRID
                setAdapterLayoutMode(newLayoutMode)
            }
            isIconChanged = !isIconChanged
        }
    }

    private fun setAdapterLayoutMode(newLayoutMode: AdapterLayoutMode) {
        val preferences = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt("layoutMode", newLayoutMode.ordinal)
        editor.apply()

        val span = if (newLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        (binding.rvProductList.layoutManager as GridLayoutManager).spanCount = span
    }

    private fun getMenus() {
        viewModel.getCategories()
        viewModel.getMenus()
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateCategory.root.isVisible = false
                binding.layoutStateCategory.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvCategory.apply {
                    isVisible = true
                    adapter = categoryAdapter
                }
                it.payload?.let { data -> categoryAdapter.submitData(data) }
            }, doOnLoading = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvCategory.isVisible = false
                }, doOnError = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategory.isVisible = false
                })
        }
        viewModel.menus.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateMenu.root.isVisible = false
                binding.layoutStateMenu.pbLoading.isVisible = false
                binding.layoutStateMenu.tvError.isVisible = false
                binding.rvProductList.apply {
                    isVisible = true
                    adapter = menuAdapter
                }
                it.payload?.let { data -> menuAdapter.submitData(data) }
            }, doOnLoading = {
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = true
                    binding.layoutStateMenu.tvError.isVisible = false
                    binding.rvProductList.isVisible = false
                }, doOnError = {
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = false
                    binding.layoutStateMenu.tvError.isVisible = true
                    binding.layoutStateMenu.tvError.text = it.exception?.message.orEmpty()
                    binding.rvProductList.isVisible = false
                }, doOnEmpty = {
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = false
                    binding.layoutStateMenu.tvError.isVisible = true
                    binding.layoutStateMenu.tvError.text = "Product not found"
                    binding.rvProductList.isVisible = false
                })
        }
    }
}
