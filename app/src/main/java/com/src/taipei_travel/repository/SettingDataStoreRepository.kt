package com.src.taipei_travel.repository

import com.src.taipei_travel.data.model.Language
import com.src.taipei_travel.data.local.datastore.Settings
import com.src.taipei_travel.data.model.DarkMode
import kotlinx.coroutines.flow.Flow

interface SettingDataStoreRepository {
    suspend fun updateDataStore(darkMode: DarkMode? = null, language: Language? = null)
    suspend fun readDataStore(): Flow<Settings>
}