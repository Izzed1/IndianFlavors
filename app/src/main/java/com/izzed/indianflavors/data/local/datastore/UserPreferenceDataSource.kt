package com.izzed.indianflavors.data.local.datastore

import com.izzed.indianflavors.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    suspend fun getUserLayoutProductPref(): Boolean
    fun getUserLayoutProductPrefFlow(): Flow<Boolean>
    suspend fun setUserLayoutProductPref(isUsingDarkMode: Boolean)
}

class UserPreferenceDataSourceImpl(private val preferenceHelper: PreferenceDataStoreHelper) :
    UserPreferenceDataSource {

    companion object {
    }

    override suspend fun getUserLayoutProductPref(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUserLayoutProductPrefFlow(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setUserLayoutProductPref(isUsingDarkMode: Boolean) {
        TODO("Not yet implemented")
    }
}
