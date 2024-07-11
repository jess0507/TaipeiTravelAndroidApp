package com.src.taipei_travel.ui.settingDetail.model

sealed class DarkModeOption(name: String, val id: Int): Option(name) {
    class LIGHT: DarkModeOption("light_mode", 1)
    class DARK: DarkModeOption("dark_mode", 2)
    class SYSTEM: DarkModeOption("system_mode",3)

    companion object {
        fun getAllOptions(): List<DarkModeOption> = listOf(LIGHT(), DARK(), SYSTEM())
    }
}