package com.src.taipei_travel.ui.share

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.taipei_travel.data.local.datastore.Settings
import com.src.taipei_travel.data.model.Option
import com.src.taipei_travel.data.model.Setting
import com.src.taipei_travel.data.model.toDarkMode
import com.src.taipei_travel.data.model.toLanguage
import com.src.taipei_travel.repository.SettingDataStoreRepository
import com.src.taipei_travel.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val settingDataStoreRepository: SettingDataStoreRepository
): ViewModel() {
    var settings = MutableLiveData<Settings>()
    var translations = MutableStateFlow<Map<String, String>>(mapOf())

    init {
        viewModelScope.launch {
            settingDataStoreRepository.readDataStore().collect {
                settings.value = it
                Timber.d("update settings from store: $it")

                settings.value?.language?.let { trans ->
                    translations.value = Constants.getTranslation(trans)
                }
            }
        }
    }

    fun updateDataStore(setting: Setting, option: Option) {
        when(setting) {
            Setting.Language -> {
                val lang = option.toLanguage() ?: return
                viewModelScope.launch {
                    settingDataStoreRepository.updateDataStore(language = lang)
                }
            }
            Setting.DarkMode -> {
                val mode = option.toDarkMode() ?: return
                viewModelScope.launch {
                    settingDataStoreRepository.updateDataStore(darkMode = mode)
                }
            }
        }
    }
}