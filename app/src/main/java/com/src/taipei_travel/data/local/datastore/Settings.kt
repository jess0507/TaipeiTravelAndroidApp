package com.src.taipei_travel.data.local.datastore

import com.src.taipei_travel.data.model.DarkMode
import com.src.taipei_travel.data.model.Language
import kotlinx.serialization.Serializable

@Serializable
data class Settings (
    val darkMode: DarkMode = DarkMode.System,
    val language: Language = Language.English
)