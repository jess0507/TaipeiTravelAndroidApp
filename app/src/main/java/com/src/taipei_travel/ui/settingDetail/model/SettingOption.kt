package com.src.taipei_travel.ui.settingDetail.model

import com.src.taipei_travel.data.local.datastore.model.Settings

sealed class SettingOption(name: String): Option(name) {

    class DarkMode: SettingOption("darkmode")

    class Language: SettingOption("language")

    fun getSubOptions(): List<Option> =
        when(this) {
            is DarkMode -> DarkModeOption.getAllOptions()
            is Language -> LanguageOption.getAllOptions()
        }

    fun getSubOptionIndex(settings: Settings): Int {
        val options: List<Option> = getSubOptions()
        return when(this) {
            is DarkMode -> {
                options.indexOfFirst {
                    it is DarkModeOption && it.id == settings.darkMode
                }
            }
            is Language -> {
                options.indexOfFirst {
                    it is LanguageOption && it.language == settings.language
                }
            }
        }
    }

    companion object {
        fun getAllOptions(): List<SettingOption> = listOf(DarkMode(), Language())

        fun getSettingOptionFromOption(option: Option): SettingOption? {
            return when(option.name) {
                "darkmode" -> DarkMode()
                "language" -> Language()
                else -> null
            }
        }
    }
}