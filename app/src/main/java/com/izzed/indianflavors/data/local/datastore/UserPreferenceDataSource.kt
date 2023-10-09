package com.izzed.indianflavors.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
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
        val PREF_USER_DARK_MODE = booleanPreferencesKey("PREF_USER_DARK_MODE")
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