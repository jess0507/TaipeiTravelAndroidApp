package com.src.taipei_travel.ui.settingDetail.model

import androidx.appcompat.app.AppCompatDelegate

sealed class DarkModeOption(name: String, val id: Int): Option(name) {
    class LIGHT: DarkModeOption("light_mode", AppCompatDelegate.MODE_NIGHT_NO)
    class DARK: DarkModeOption("dark_mode", AppCompatDelegate.MODE_NIGHT_YES)
    class SYSTEM: DarkModeOption("system_mode", AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)

    companion object {
        fun getAllOptions(): List<DarkModeOption> = listOf(LIGHT(), DARK(), SYSTEM())
    }
}