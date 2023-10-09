package com.izzed.indianflavors.presentation.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.izzed.indianflavors.data.dummy.DummyCategoryDataSource
import com.izzed.indianflavors.data.dummy.DummyCategoryDataSourceImpl
import com.izzed.indianflavors.data.local.database.AppDatabase
import com.izzed.indianflavors.data.local.database.datasource.ProductDatabaseDataSource
import com.izzed.indianflavors.data.repository.ProductRepository
import com.izzed.indianflavors.data.repository.ProductRepositoryImpl
import com.izzed.indianflavors.databinding.FragmentHomeBinding
import com.izzed.indianflavors.model.Product
import com.izzed.indianflavors.presentation.feature.detail.DetailActivity
import com.izzed.indianflavors.presentation.feature.home.adapter.HomeAdapter
import com.izzed.indianflavors.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter: HomeAdapter by lazy {
        HomeAdapter(onProductClicked = {
            navigateToDetail(it)
        })
    }

    private fun navigateToDetail(item: Product) {
        DetailActivity.startActivity(requireContext(), item)
    }

    private val viewModel: HomeViewModel by viewModels {
        val cds: DummyCategoryDataSource = DummyCategoryDataSourceImpl()
        val database = AppDatabase.getInstance(requireContext())
        val productDao = database.productDao()
        val productDataSource = ProductDatabaseDataSource(productDao)
        val repo: ProductRepository = ProductRepositoryImpl(productDataSource, cds)
        GenericViewModelFactory.create(HomeViewModel(repo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        fetchData()
        setupRv()
    }

    private fun setupRv() {

    }

    private fun fetchData() {
        viewModel.homeData.observe(viewLifecycleOwner) {
            adapter.submitData(it)
        }
    }

    private fun setupList() {
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
    }
}