package com.izzed.indianflavors.presentation.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.izzed.indianflavors.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val userPreferenceDataSource: UserPreferenceDataSource) : ViewModel() {

}