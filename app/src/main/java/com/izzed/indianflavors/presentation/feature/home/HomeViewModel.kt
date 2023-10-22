package com.izzed.indianflavors.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.izzed.indianflavors.data.repository.ProductRepository
import com.izzed.indianflavors.model.Category
import com.izzed.indianflavors.model.Menu
import com.izzed.indianflavors.presentation.feature.home.adapter.model.HomeSection
import com.izzed.indianflavors.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ProductRepository) : ViewModel() {

    // Buat Livedata category dan menu
    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()
    val categories: LiveData<ResultWrapper<List<Category>>>
        get() = _categories

    private val _menus = MutableLiveData<ResultWrapper<List<Menu>>>()
    val menus : LiveData<ResultWrapper<List<Menu>>>
        get() = _menus

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCategories().collect{
                _categories.postValue(it)
            }
        }
    }

    fun getMenus(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMenus(category).collect{
                _menus.postValue(it)
            }
        }
    }
}