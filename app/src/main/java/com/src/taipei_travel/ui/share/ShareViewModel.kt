package com.src.taipei_travel.ui.share

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.taipei_travel.ui.settingDetail.SettingDetailState
import com.src.taipei_travel.domain.SettingDataStoreRepository
import com.src.taipei_travel.ui.settingDetail.model.DarkModeOption
import com.src.taipei_travel.ui.settingDetail.model.LanguageOption
import com.src.taipei_travel.ui.settingDetail.model.Option
import com.src.taipei_travel.data.remote.model.Attraction
import com.src.taipei_travel.ui.settingDetail.model.SettingOption
import com.src.taipei_travel.data.local.datastore.model.Settings
import com.src.taipei_travel.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val settingDataStoreRepository: SettingDataStoreRepository
): ViewModel() {
    var settings = MutableLiveData<Settings>()

    var settingOption = MutableStateFlow<SettingOption>(SettingOption.DarkMode())
    var selectedPosition = MutableLiveData(-1)
    var translations = MutableStateFlow<Map<String, String>>(mapOf())

    val settingDetailState : StateFlow<SettingDetailState> = combine(
        settingOption,
        translations
    ) { settingOption, translations ->
        SettingDetailState(settingOption, translations)
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SettingDetailState()
    )


    init {
        viewModelScope.launch {
            settingDataStoreRepository.readDataStore().collect {
                settings.value = it
                Timber.d("update settings from store: $it")
                triggerDarkMode(it.darkMode)

                val pos: Int = settingOption.value.getSubOptionIndex(it)
                selectedPosition.value = pos
                Timber.d("pos: $pos")

                settings.value?.language?.let { trans ->
                    translations.value = Constants.getTranslation(trans)
                }
            }
        }
    }

    fun updateDataStore(option: Option) {
        when(option) {
            is DarkModeOption -> {
                viewModelScope.launch {
                    settingDataStoreRepository.updateDataStore(darkMode = option.id)
                }
            }
            is LanguageOption -> {
                viewModelScope.launch {
                    settingDataStoreRepository.updateDataStore(language = option.language)
                }
            }
        }
    }

    private fun triggerDarkMode(mode: Int) {
        if (mode == AppCompatDelegate.getDefaultNightMode()) return
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}