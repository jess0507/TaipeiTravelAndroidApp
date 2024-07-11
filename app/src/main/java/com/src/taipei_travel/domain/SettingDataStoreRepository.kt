package com.src.taipei_travel.domain

import com.src.taipei_travel.data.local.datastore.model.Language
import com.src.taipei_travel.data.local.datastore.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingDataStoreRepository {
    suspend fun updateDataStore(darkMode: Int? = null, language: Language? = null)
    suspend fun readDataStore(): Flow<Settings>
}