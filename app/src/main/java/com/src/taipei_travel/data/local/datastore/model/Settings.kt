package com.src.taipei_travel.data.local.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings (
    val darkMode: Int = 1,
    val language: Language = Language.English
)