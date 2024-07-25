package com.src.taipei_travel.data.local.datastore.model

import androidx.appcompat.app.AppCompatDelegate
import kotlinx.serialization.Serializable

@Serializable
data class Settings (
    val darkMode: Int = AppCompatDelegate.MODE_NIGHT_UNSPECIFIED,
    val language: Language = Language.English
)