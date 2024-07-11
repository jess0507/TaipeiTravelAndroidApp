package com.src.taipei_travel.ui.settingDetail

import com.src.taipei_travel.ui.settingDetail.model.SettingOption

data class SettingDetailState(
    val settingOption: SettingOption = SettingOption.DarkMode(),
    val translations: Map<String, String> = mapOf(),
)