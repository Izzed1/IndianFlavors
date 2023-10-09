package com.izzed.indianflavors.presentation.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.izzed.indianflavors.R
import com.izzed.indianflavors.data.local.datastore.UserPreferenceDataSourceImpl
import com.izzed.indianflavors.data.local.datastore.appDataStore
import com.izzed.indianflavors.databinding.ActivityMainBinding
import com.izzed.indianflavors.utils.GenericViewModelFactory
import com.izzed.indianflavors.utils.PreferenceDataStoreHelperImpl

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupButtonNav()

    }

    private fun setupButtonNav() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_main)

        navView.setupWithNavController(navController)
    }
}