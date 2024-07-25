package com.src.taipei_travel.data.model

import androidx.appcompat.app.AppCompatDelegate
import kotlinx.serialization.Serializable

@Serializable
enum class DarkMode(val title: String, val mode: Int) {
    Light("Light", AppCompatDelegate.MODE_NIGHT_NO),
    Dark("Dark", AppCompatDelegate.MODE_NIGHT_YES),
    System("System", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
}