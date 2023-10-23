package com.izzed.indianflavors.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.izzed.indianflavors.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repository.isLoggedIn()
}