package com.src.taipei_travel.domain

import androidx.datastore.core.DataStore
import com.src.taipei_travel.data.local.datastore.model.Language
import com.src.taipei_travel.data.local.datastore.model.Settings
import com.src.taipei_travel.di.Dispatcher
import com.src.taipei_travel.di.NiaDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SettingDataStoreRepositoryImpl @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) val scope: CoroutineDispatcher,
    private val dataStore: DataStore<Settings>,
): SettingDataStoreRepository {
    override suspend fun updateDataStore(darkMode: Int?, language: Language?) {
        Timber.d("darkMode: $darkMode, language: $language")
        withContext(scope) {
            try {
                dataStore.updateData {
                    it.copy(
                        darkMode = darkMode ?: it.darkMode,
                        language = language ?: it.language
                    )
                }
            } catch (e: Exception) {
                Timber.d(e)
                throw e
            }
        }
    }
    override suspend fun readDataStore(): Flow<Settings> {
        return withContext(scope) {
            try {
                var settings: Flow<Settings> = dataStore.data
                Timber.d("settings: $settings")
                return@withContext settings
            } catch (e: Exception) {
                Timber.d(e)
                throw e
            }
        }
    }
}